package cn.flowboot.e.commerce.rocket;

import cn.flowboot.e.commerce.vo.RocketMQMessage;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * <h1>通过RocketMQ发送消息</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/11
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class RocketMQProducer {

    private static final String TOPIC = "rocket-mq-topic";

    private final RocketMQTemplate rocketMQTemplate;

    /**
     * <h2> sendMessageWithValue -使用同步的方式发送消息,不指定key 和tag <h2>
     * version: 1.0 - 2022/3/11
     * @param value 值
     */
    public void sendMessageWithValue(String value){
        // 随机选择一个Topic的Message Queue 发送消息
        SendResult sendResult = rocketMQTemplate.syncSend(TOPIC, value);
        log.info("sendMessageWithValue result : [{}]", JSON.toJSONString(sendResult));

        SendResult syncSendOrderly = rocketMQTemplate.syncSendOrderly(TOPIC, value, "RocketMQ-Key");
        log.info("sendMessageWithValue orderly result : [{}]", JSON.toJSONString(syncSendOrderly));

    }

    /**
     * <h2> sendMessageWithValue -使用异步的方式发送消息,指定key <h2>
     * version: 1.0 - 2022/3/11
     * @param  key
     * @param value 值
     */
    public void sendMessageWithKey(String key,String value){
        Message<String> message = MessageBuilder.withPayload(value)
                .setHeader(RocketMQHeaders.KEYS,key).build();

        //异步发送消息，设定回调

        rocketMQTemplate.asyncSend(TOPIC, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("sendMessageWithKey onSuccess result : [{}]", JSON.toJSONString(sendResult));

            }

            @Override
            public void onException(Throwable throwable) {
                log.info("sendMessageWithKey onException result : [{}]", throwable.getMessage(),throwable);
            }
        });
    }

    /**
     * <h2> sendMessageWithTag - 使用同步的方式发送消息, 带有 tag, 且发送的是 Java Pojo<h2>
     * version: 1.0 - 2022/3/11
     * @param tag
     * @param value
     */
    public void sendMessageWithTag(String tag, String value) {

        RocketMQMessage rocketMQMessage = JSON.parseObject(value, RocketMQMessage.class);
        SendResult sendResult = rocketMQTemplate.syncSend(String.format("%s:%s", TOPIC, tag), rocketMQMessage);
        log.info("sendMessageWithTag result: [{}]", JSON.toJSONString(sendResult));
    }

    /**
     * <h2> sendMessageWithAll - 使用同步的方式发送消息, 带有 key 和 tag<h2>
     * version: 1.0 - 2022/3/11
     * @param key
     * @param tag
     * @param value
     */
    public void sendMessageWithAll(String key, String tag, String value) {

        Message<String> message = MessageBuilder.withPayload(value)
                .setHeader(RocketMQHeaders.KEYS, key).build();
        SendResult sendResult = rocketMQTemplate.syncSend(String.format("%s:%s", TOPIC, tag), message);
        log.info("sendMessageWithAll result: [{}]", JSON.toJSONString(sendResult));
    }

    private static final String DELAY_TOPIC = "rocket-mq-delay-topic";

    private SendCallback callback = new SendCallback() {
        @Override
        public void onSuccess(SendResult sendResult) {

        }

        @Override
        public void onException(Throwable throwable) {

        }
    };

    /**
     * 发送延时消息
     * 延时消息等级分为18个：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * @param message       消息
     * @param delayLevel 延迟等级
     * @author tyg
     * @date 2021-03-24 14:55
     * @return void
     */
    public void sendDelayMessage(String message,Integer delayLevel){
        log.info("sendDelayMessage now time : {}", LocalDateTime.now());
        rocketMQTemplate.asyncSend(RocketMQProducer.DELAY_TOPIC,MessageBuilder.withPayload(message).build(),callback,3000,delayLevel);
    }





}
