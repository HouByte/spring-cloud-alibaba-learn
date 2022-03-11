package cn.flowboot.e.commerce.rocket;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * <h1>RocketMQ 消费者 消息扩展</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/11
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "rocket-mq-topic",
        consumerGroup = "springboot-rocketmq-message-ext"
)
public class RocketMQConsumerMessageExt implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt messageExt) {
        String value = new String(messageExt.getBody());
        log.info("consumer message in RocketMQConsumerMessageExt : [{}],[{}]",messageExt.getKeys(),value);
        log.info("MessageExt :[{}]", JSON.toJSONString(messageExt));
    }
}
