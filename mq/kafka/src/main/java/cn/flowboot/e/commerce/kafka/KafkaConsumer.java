package cn.flowboot.e.commerce.kafka;

import cn.flowboot.e.commerce.vo.KafkaMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/10
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumer {

    private final ObjectMapper mapper;



    /**
     * <h2>监听 Kafka 消息并消费</h2>
     * */
    @KafkaListener(topics = {"kafka-springboot"}, groupId = "springboot-kafka")
    public void listener01(ConsumerRecord<String, String> record) throws Exception {

        String key = record.key();
        String value = record.value();

        KafkaMessage kafkaMessage = mapper.readValue(value, KafkaMessage.class);
        log.info("in listener01 consume kafka message: [{}], [{}]",
                key, mapper.writeValueAsString(kafkaMessage));
    }

    /**
     * <h2>监听 Kafka 消息并消费</h2>
     * */
    @KafkaListener(topics = {"kafka-springboot"}, groupId = "springboot-kafka-1")
    public void listener02(ConsumerRecord<?, ?> record) throws Exception {

        Optional<?> _kafkaMessage = Optional.ofNullable(record.value());
        if (_kafkaMessage.isPresent()) {
            Object message = _kafkaMessage.get();
            KafkaMessage kafkaMessage = mapper.readValue(message.toString(),
                    KafkaMessage.class);
            log.info("in listener02 consume kafka message: [{}]",
                    mapper.writeValueAsString(kafkaMessage));
        }
    }
}

