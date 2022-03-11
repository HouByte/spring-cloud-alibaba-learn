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
public class StreamMessage {
    private Integer id;
    private String projectName;
    private String org;
    private String author;
    private String version;

    public static StreamMessage defaultMessage(){
        return new StreamMessage(
            1,
            "stream-kafka-client",
            "flowboot.cn",
            "Vincent Vic",
            "1.0"
        );
    }
}
