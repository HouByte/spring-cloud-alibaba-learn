package cn.flowboot.e.commerce.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <h1>  </h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/11
 */
@Slf4j
@Component
public class RabbitMQConsumer {

    /**
     * workqueue模式（拿到消息即销毁）
     * receiveA,receiveB 可以分流队列，在多实例当中可以被其中一个消费
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue("rabbit-mq-topic"))
    public void receiveA(String message) {
        log.info("receive message by tipic rabbit-mq-topic [Client A] : {}",message);
    }

    /**
     * workqueue模式（拿到消息即销毁）
     * receiveA,receiveB 可以分流队列，在多实例当中可以被其中一个消费
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue("rabbit-mq-topic"))
    public void receivelB(String message) {
        log.info("receive message by tipic rabbit-mq-topic [Client B] : {}",message);
    }

    /**
     * Publish模型（发布订阅/fanout模型）
     * receiveC,receiveD 可以分流队列，在多实例当中可以被其中一个消费
     * @param message
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//声明临时队列
                    exchange = @Exchange(value = "fanout-rabbit-mq-exchange",type = "fanout")
            )
    })
    public void receiveC(String message) {
        log.info("receive message by fanout [Client C] : {}",message);

    }

    /**
     * Publish模型（发布订阅/fanout模型）
     * receiveC,receiveD 可以分流队列，在多实例当中可以被其中一个消费
     * @param message
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//声明临时队列
                    exchange = @Exchange(value = "fanout-rabbit-mq-exchange",type = "fanout")
            )
    })
    public void receiveD(String message) {
        log.info("receive message by fanout [Client D] : {}",message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//声明临时队列
                    exchange = @Exchange(value = "routing-rabbit-mq-exchange", type = "direct"),
                    key ={"error"}
            )
    })
    public void receiveE(String message) {
        log.info("receive message by fanout [Client E] : {}",message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//声明临时队列
                    exchange = @Exchange(value = "routing-rabbit-mq-exchange",type = "direct"),
                    key ={"error","info"}
            )
    })
    public void receiveF(String message) {
        log.info("receive message by fanout [Client F] : {}",message);

    }

}
