package cn.flowboot.e.commerce.rocket;

import cn.flowboot.e.commerce.vo.RocketMQMessage;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * <h1>RocketMQ 消费者 -对应延迟消费者</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/11
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "rocket-mq-delay-topic",
        consumerGroup = "springboot-rocketmq-delay"
)
public class RocketMQConsumerDelay implements RocketMQListener<RocketMQMessage> {
    @Override
    public void onMessage(RocketMQMessage message) {
        log.info("Now time : {}  receive delay message: [{}] ", LocalDateTime.now(),JSON.toJSONString(message));

    }
}
