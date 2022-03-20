package cn.flowboot.e.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <h1>Kafka 客户端</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/10
 */
@SpringBootApplication
public class KafkaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaClientApplication.class,args);
    }
}
