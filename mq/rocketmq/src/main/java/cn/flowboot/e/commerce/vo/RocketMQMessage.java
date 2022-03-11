package cn.flowboot.e.commerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RocketMQMessage {
    private Integer id;
    private String projectName;
}
