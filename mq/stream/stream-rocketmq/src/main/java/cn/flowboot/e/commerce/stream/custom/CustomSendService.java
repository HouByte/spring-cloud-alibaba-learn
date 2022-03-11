package cn.flowboot.e.commerce.stream.custom;

import cn.flowboot.e.commerce.vo.StreamMessage;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * <h1>使用自定义的通信信道 CustomSource 实现消息的发送</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/11
 */
@Slf4j
@RequiredArgsConstructor
@EnableBinding(CustomSource.class)
@Component
public class CustomSendService {
    private final CustomSource customSource;

    /**
     * <h2>使用自定义的输出信道发送消息</h2>
     * */
    public void sendMessage(StreamMessage message) {

        String _message = JSON.toJSONString(message);
        log.info("in CustomSendService send message: [{}]", _message);
        customSource.customOutput().send(MessageBuilder.withPayload(_message).build());
    }

}
