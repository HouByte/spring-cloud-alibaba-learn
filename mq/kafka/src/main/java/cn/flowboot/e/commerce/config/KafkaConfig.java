package cn.flowboot.e.commerce.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>通过代码自定义Kafka配置</h1>
 * <h2>也可以通过yml文件配置-非必须</h2>
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/10
 */
//@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * <h2> producerFactory -  Kafka Producer 工厂配置<h2>
     * version: 1.0 - 2022/3/10
     * @return {@link ProducerFactory< String, String> }
     */
    @Bean
    public ProducerFactory<String,String> producerFactory(){
        Map<String,Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    /**
     * <h2> kafkaTemplate - 客户端<h2>
     * version: 1.0 - 2022/3/10
     * @return {@link KafkaTemplate< String, String> }
     */
    @Bean
    public KafkaTemplate<String,String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * <h2> consumerFactory - Kafka Consumer 工厂配置<h2>
     * version: 1.0 - 2022/3/10
     * @return {@link ConsumerFactory< String, String> }
     */
    @Bean
    public ConsumerFactory<String,String> consumerFactory(){
        Map<String,Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,50);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * <h2> kafkaListenerContainerFactory - 监听器工厂配置<h2>
     * version: 1.0 - 2022/3/10
     * @param
     * @return {@link ConcurrentKafkaListenerContainerFactory< String, String> }
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,String> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,String> factory = new ConcurrentKafkaListenerContainerFactory<String,String>();
        //并发数
        factory.setConcurrency(3);
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }

}
