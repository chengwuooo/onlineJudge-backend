package com.chengwu.questionservice.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyMessageProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息到指定的交换机。
     *
     * 通过RabbitMQ的模板方法，将消息转换为适当的形式并发送到指定的交换机。
     * 交换机根据路由键将消息路由到相应的队列中。
     *
     * @param exchange 消息将被发送到的交换机的名称。交换机根据定义的类型和绑定来处理消息。
     * @param routingKey 用于路由消息的键。路由键与交换机的绑定匹配，以确定消息应被发送到哪个队列。
     * @param message 要发送的实际消息内容。
     */
    public void sendMessage(String exchange, String routingKey, String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

}