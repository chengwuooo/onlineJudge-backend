package com.chengwu.judgeservice.rabbitmq;

import com.chengwu.judgeservice.judge.JudgeService;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class MyMessageConsumer {

    @Resource
    private JudgeService judgeService;

    /**
     * 启动RabbitMQ监听器，用于接收代码评测请求。
     * 监听特定的队列code_queue，手动确认消息处理完成。
     *
     * @param message 接收到的字符串消息，包含需要评测的题目提交ID。
     * @param channel AMQP通道，用于向RabbitMQ服务器发送确认或拒绝消息。
     * @param deliveryTag 消息的投递标签，用于唯一标识一个消息，在确认或拒绝消息时使用。
     */
    @SneakyThrows
    @RabbitListener(queues = {"code_queue"}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        // 记录接收到的消息
        log.info("收到消息 = {}", message);
        long questionSubmitId = Long.parseLong(message);
        try {
            // 调用评测服务进行代码评测
            judgeService.doJudge(questionSubmitId);
            // 如果评测成功，确认消息处理完成，消息从队列中删除。
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            // 确认消息处理完成，但设置requeue为true，使消息重新入队，进行重试。
            channel.basicAck(deliveryTag, true);
        }
    }


}