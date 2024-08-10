package com.chengwu.judgeservice.rabbitmq;

import com.chengwu.judgeservice.judge.JudgeService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
@Slf4j
public class MyMessageConsumer {

    @Resource
    private JudgeService judgeService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 启动RabbitMQ监听器，用于接收代码评测请求。
     * 监听特定的队列code_queue，手动确认消息处理完成，最多重试2次。
     *
     * @param message     接收到的字符串消息，包含需要评测的题目提交ID。
     * @param channel     AMQP通道，用于向RabbitMQ服务器发送确认或拒绝消息。
     * @param deliveryTag 消息的投递标签，用于唯一标识一个消息，在确认或拒绝消息时使用。
     */
    @RabbitListener(queues = {"code_queue"}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        int retryCount = 0;
        while (retryCount < 2) {
            log.info("收到消息 = {}", message);
            long questionSubmitId = Long.parseLong(message);

            try {
                // 如果评测成功，确认消息处理完成，消息从队列中删除。
                judgeService.doJudge(questionSubmitId);
                if (channel.isOpen()) {
                    channel.basicAck(deliveryTag, false);
                } else {
                    log.error("Channel is closed; cannot ack message.");
                }
                break;
            } catch (Exception e) {
                log.error("处理消息时出现异常", e);
                retryCount++;
                if (retryCount < 1) {
                    log.warn("评测失败，将尝试第{}次重试", retryCount);
                    channel.basicNack(deliveryTag, false, true);
                } else {
                    log.error("评测失败，已达到最大重试次数");
                    if (channel.isOpen()) {
                        try {
                            channel.basicAck(deliveryTag, false);
                        } catch (IOException ioException) {
                            log.error("发送拒绝消息失败", ioException);
                        }
                    } else {
                        log.error("Channel is closed; cannot nack message.");
                    }
                }
            }
        }
    }

}
