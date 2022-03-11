package cn.flowboot.e.commerce.rocket;

import cn.flowboot.e.commerce.vo.RocketMQMessage;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * <h1>RocketMQ 消费者 指定带有tag的消息</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/11
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "rocket-mq-topic",
        consumerGroup = "springboot-rocketmq-tag-string",
        selectorExpression = "rocket"
)
public class RocketMQConsumerTagString implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        RocketMQMessage rocketMQMessage = JSON.parseObject(message, RocketMQMessage.class);
        log.info("consumer message in RocketMQConsumerString : [{}]",message);
    }
}
