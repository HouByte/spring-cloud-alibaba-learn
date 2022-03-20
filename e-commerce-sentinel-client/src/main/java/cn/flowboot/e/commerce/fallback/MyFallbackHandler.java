package cn.flowboot.e.commerce.fallback;

import cn.flowboot.e.commerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/18
 */
@Slf4j
public class MyFallbackHandler {
    /**
     * <h2>remoteConsumer 方法的 fallback</h2>
     * */
    public static CommonResponse remoteConsumerFallback() {
        log.error("remote consumer service fallback");
        return CommonResponse.fail("fallback");
    }

    /**
     * <h2>ignoreException 方法的 fallback</h2>
     * */
    public static CommonResponse ignoreExceptionFallback(Integer code) {
        log.error("ignore exception input code: [{}] has trigger exception", code);
        return CommonResponse.fail("ignoreExceptionFallback");

    }

}
