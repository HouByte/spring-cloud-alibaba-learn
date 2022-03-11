package cn.flowboot.e.commerce.stream;

import cn.flowboot.e.commerce.vo.StreamMessage;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * <h1>使用默认通信信道发送</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/11
 */
@Slf4j
@RequiredArgsConstructor
@EnableBinding(Source.class)
@Component
public class DefaultSendService {

    private final Source source;

    /**
     * <h2> sendMessage - 使用默认的输出信道发送消息<h2>
     * version: 1.0 - 2022/3/11
     * @param message
     */
    public void sendMessage(StreamMessage message){
        String _message = JSON.toJSONString(message);
        log.info("in DefaultSendService send message : [{}]",_message);
        source.output().send(MessageBuilder.withPayload(_message).build());
    }
}
