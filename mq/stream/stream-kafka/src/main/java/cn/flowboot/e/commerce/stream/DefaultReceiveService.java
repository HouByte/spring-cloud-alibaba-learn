package cn.flowboot.e.commerce.stream;

import cn.flowboot.e.commerce.vo.StreamMessage;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

/**
 * <h1>使用默认通信信道接收</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/11
 */
@Slf4j
@RequiredArgsConstructor
@EnableBinding(Sink.class)
@Component
public class DefaultReceiveService {

    /**
     * <h2> receiveMessage - 使用默认的输入信道发送消息 <h2>
     * version: 1.0 - 2022/3/11
     * @param payload 消息
     */
    @StreamListener(Sink.INPUT)
    public void receiveMessage(Object payload){
        log.info("in DefaultReceiveService receive message start");
        StreamMessage message = JSON.parseObject(payload.toString(), StreamMessage.class);
        log.info("in DefaultReceiveService receive message success: [{}]",JSON.toJSONString(message));
    }
}
