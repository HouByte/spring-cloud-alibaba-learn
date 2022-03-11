package cn.flowboot.e.commerce.controller;

import cn.flowboot.e.commerce.rocket.RocketMQProducer;
import cn.flowboot.e.commerce.vo.RocketMQMessage;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("rocket-mq")
public class RocketMQController {

    private final RocketMQProducer rocketMQProducer;

    private static final RocketMQMessage message = new RocketMQMessage(1, "RocketMQ");
    /**
     * <h2>发送 RocketMQ 消息</h2>
     * */
    @GetMapping("/send-message/value")
    public void sendMessageWithValue(){

        rocketMQProducer.sendMessageWithValue(JSON.toJSONString(message));
    }

    /**
     * <h2>发送 RocketMQ 消息</h2>
     * */
    @GetMapping("/send-message/key")
    public void sendMessageWithKey(){

        rocketMQProducer.sendMessageWithKey("RocketMQ",JSON.toJSONString(message));
    }
    /**
     * <h2>发送 RocketMQ 消息</h2>
     * */
    @GetMapping("/send-message/tag")
    public void sendMessageWithTag(){

        rocketMQProducer.sendMessageWithTag("rocket",JSON.toJSONString(message));
    }
    /**
     * <h2>发送 RocketMQ 消息</h2>
     * */
    @GetMapping("/send-message/all")
    public void sendMessageWithAll(){

        rocketMQProducer.sendMessageWithAll("RocketMQ","rocket",JSON.toJSONString(message));
    }

    /**
     * 发送延迟消息
     * 延时消息等级分为18个：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * delayLevel对应延迟等级就是上面的时间 共18个等级
     * rocketmq只能指定延迟等级而不能自定义延迟时间，如果想自定义需要阿里巴巴提供的企业版rocketmq要收费
     */
    @GetMapping("sendDelay")
    public void sendDelay(){
        // 延时消息等级分为18个：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        rocketMQProducer.sendDelayMessage(JSON.toJSONString(message),4);

    }


}
