package cn.flowboot.e.commerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>自定义Kafka 传递的消息对象</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/10
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class KafkaMessage {
    private Integer id;
    private String projectName;
}
