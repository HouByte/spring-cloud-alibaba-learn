package cn.flowboot.e.commerce.controller;

import cn.flowboot.e.commerce.kafka.KafkaProducer;
import cn.flowboot.e.commerce.vo.KafkaMessage;
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
@RequestMapping("kafka")
public class KafkaController {

    private final ObjectMapper mapper;
    private final KafkaProducer kafkaProducer;

    /**
     * <h2>发送 kafka 消息</h2>
     * */
    @GetMapping("/send-message")
    public void sendMessage(@RequestParam(required = false) String key,
                            @RequestParam String topic) throws Exception {

        KafkaMessage message = new KafkaMessage(1, "kafka");
        kafkaProducer.sendMessage(key, mapper.writeValueAsString(message), topic);
    }

}
