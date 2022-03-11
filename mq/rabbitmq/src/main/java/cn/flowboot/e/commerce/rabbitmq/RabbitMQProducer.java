package cn.flowboot.e.commerce.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * <h1>RabbitMQ</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/11
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;
    private static final String TOPIC = "rabbit-mq-topic";
    private static final String FANOUT_EXCHANGE = "fanout-rabbit-mq-exchange";
    private static final String ROUTING_EXCHANGE = "routing-rabbit-mq-exchange";


    /**
     * <h2> sendMessage - workqueue模式<h2>
     * version: 1.0 - 2022/3/11
     * @param message
     */
    public void sendMessage(String message){
        log.info("send message rabbit mq : {}" ,message);
        rabbitTemplate.convertAndSend(TOPIC,message);
    }


    /**
     * <h2> testfanout - 发布订阅模式<h2>
     * version: 1.0 - 2022/3/11
     * @param message
     */
    public void sendMessageByExchange(String message){
        rabbitTemplate.convertAndSend(FANOUT_EXCHANGE, "",message);
    }


    /**
     * <h2> sendMessageByRouting - Routing（静态路由模型）<h2>
     * version: 1.0 - 2022/3/11
     * @param message
     */
    public void sendMessageByRouting(String message,String routingKey){
        rabbitTemplate.convertAndSend(ROUTING_EXCHANGE, routingKey,message);
    }

}
