package cn.flowboot.e.commerce.stream.custom;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * <h1>自定义输出信道</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/11
 */
public interface CustomSource {
    String OUTPUT = "customOutput";

    /** 输出信道的名称是 customOutput, 需要使用 Stream 绑定器在 yml 文件中声明 */
    @Output(CustomSource.OUTPUT)
    MessageChannel customOutput();

}
