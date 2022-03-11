package cn.flowboot.e.commerce.partition;

import cn.flowboot.e.commerce.vo.StreamMessage;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.PartitionKeyExtractorStrategy;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * <h1>自定义从 Message 中提取 partition key 的策略</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/11
 */

@Slf4j
@Component
public class StreamPartitionKeyExtractorStrategy implements PartitionKeyExtractorStrategy {

    @Override
    public Object extractKey(Message<?> message) {

        StreamMessage qinyiMessage = JSON.parseObject(
                message.getPayload().toString(), StreamMessage.class
        );
        // 自定义提取 key
        String key = qinyiMessage.getProjectName();
        log.info("SpringCloud Stream Partition Key: [{}]", key);
        return key;
    }

}
