package cn.flowboot.e.commerce.stream.custom;

import cn.flowboot.e.commerce.vo.StreamMessage;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * <h1>使用自定义的输入信道实现消息的接收</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/11
 */
@Slf4j
@RequiredArgsConstructor
@EnableBinding(CustomSink.class)
@Component
public class CustomReceiveService {

    /**
     * <h2> receiveMessage - 使用自定义的输入信道接收消息<h2>
     * version: 1.0 - 2022/3/11
     * @param payload 消息
     */
    @StreamListener(CustomSink.INPUT)
    public void receiveMessage(@Payload Object payload) {

        log.info("in CustomReceiveService consume message start");
        StreamMessage message = JSON.parseObject(payload.toString(), StreamMessage.class);
        log.info("in CustomReceiveService consume message success: [{}]", JSON.toJSONString(message));
    }

}
