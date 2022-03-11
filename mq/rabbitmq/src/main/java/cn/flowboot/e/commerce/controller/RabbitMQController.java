package cn.flowboot.e.commerce.controller;

import cn.flowboot.e.commerce.rabbitmq.RabbitMQProducer;
import cn.flowboot.e.commerce.vo.RabbitMQMessage;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/10
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("rabbit-mq")
public class RabbitMQController {

    private final RabbitMQProducer rabbitMQProducer;

    private static final RabbitMQMessage message = new RabbitMQMessage(1, "RabbitMQ");
    /**
     * <h2>发送 RabbitMQ 消息 - workqueue模式（拿到消息即销毁）</h2>
     * */
    @GetMapping("/send-message")
    public void sendMessage(){

        rabbitMQProducer.sendMessage(JSON.toJSONString(message));
    }

    /**
     * <h2>发送 RabbitMQ 消息 - Publish模型（发布订阅/fanout模型）</h2>
     * */
    @GetMapping("/send-message/publish")
    public void sendMessageByPublish(){
        rabbitMQProducer.sendMessageByExchange(JSON.toJSONString(message));
    }
    /**
     * <h2>发送 RabbitMQ 消息 - Routing（静态路由模型）</h2>
     * */
    @GetMapping("/send-message/routing")
    public void sendMessageByRouting(String routing){
        rabbitMQProducer.sendMessageByRouting(JSON.toJSONString(message),routing);
    }
}
