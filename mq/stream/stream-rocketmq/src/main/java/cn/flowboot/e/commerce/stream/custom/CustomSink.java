package cn.flowboot.e.commerce.stream.custom;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * <h1>自定义输入信道</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/11
 */
public interface CustomSink {
    String INPUT = "customInput";

    /** 输入信道的名称是 customInput, 需要使用 Stream 绑定器在 yml 文件中配置*/
    @Input(CustomSink.INPUT)
    SubscribableChannel customInput();

}
