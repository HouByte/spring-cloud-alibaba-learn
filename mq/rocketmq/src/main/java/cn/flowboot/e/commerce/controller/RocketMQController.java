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

    private final ObjectMapper mapper;
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



}
