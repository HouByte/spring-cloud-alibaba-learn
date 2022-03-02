package cn.flowboot.e.commerce.advice;

import cn.flowboot.e.commerce.annotation.IgnoreResponseAdvice;
import cn.flowboot.e.commerce.vo.CommonResponse;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * <h1>实现统一响应</h1>
 * @RestControllerAdvice value 限制生效的包
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/02
 */
@RestControllerAdvice(value = "cn.flowboot.e.commerce")
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object>  {

    /**
     * 判断是否需要被处理
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return !methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)
                && !Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(IgnoreResponseAdvice.class);
    }

    /**
     * 处理
     */
    @Override
    @SuppressWarnings("all")
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
                                  Class selectedConverterType, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        CommonResponse response = CommonResponse.success(o);
        if (null == o){
            return CommonResponse.success();
        } else if (o instanceof  CommonResponse){
            return  o;
        } else if (o instanceof String) {
            response.setData(o);
            return JSONObject.toJSONString(response);
        } else {
            return response;
        }
    }
}
