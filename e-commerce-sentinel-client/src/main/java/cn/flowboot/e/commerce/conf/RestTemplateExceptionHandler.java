package cn.flowboot.e.commerce.conf;

import cn.flowboot.e.commerce.vo.CommonResponse;
import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;

/**
 * <h1>RestTemplate 在限流或异常的兜底方法</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/18
 */
@Slf4j
public class RestTemplateExceptionHandler {

    /**
     * <h2> handleBlock - 限流处理方法<h2>
     * version: 1.0 - 2022/3/18
     * @param request 请求
     * @param body 数据
     * @param execution 请求链路
     * @param ex 限流处理方法
     * @return {@link SentinelClientHttpResponse }
     */
    public static SentinelClientHttpResponse handleBlock(HttpRequest request, byte[] body, ClientHttpRequestExecution execution,BlockException ex){
        log.error("handler restTemplate  block exception : [{}], [{}]", request.getURI().getPath(),request.getClass().getCanonicalName());
        return new SentinelClientHttpResponse(
                JSON.toJSONString(CommonResponse.fail("服务限流",request.getClass().getCanonicalName()))
        );
    }

    /**
     * <h2> handleFallback - 异常处理方法<h2>
     * version: 1.0 - 2022/3/18
     * @param request 请求
     * @param body 数据
     * @param execution 请求链路
     * @param ex 限流处理方法
     * @return {@link SentinelClientHttpResponse }
     */
    public static SentinelClientHttpResponse handleFallback(HttpRequest request, byte[] body, ClientHttpRequestExecution execution,BlockException ex){
        log.error("handler restTemplate  block exception : [{}], [{}]", request.getURI().getPath(),request.getClass().getCanonicalName());
        return new SentinelClientHttpResponse(
                JSON.toJSONString(CommonResponse.fail("服务异常",request.getClass().getCanonicalName()))
        );
    }

}
