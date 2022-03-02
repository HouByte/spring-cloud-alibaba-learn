package cn.flowboot.e.commerce.advice;

import cn.flowboot.e.commerce.annotation.IgnoreResponseAdvice;
import cn.flowboot.e.commerce.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
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
public class CommonResponseDataAdvice implements ResponseBodyAdvice {

    /**
     * 判断是否需要被处理
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)
                || Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(IgnoreResponseAdvice.class)){
            return false;
        }
        return true;
    }

    /**
     * 处理
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
                                  Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        CommonResponse<Object> response = new CommonResponse<Object>(0,"",null);
        if (null == o){
            return response;
        } else if (o instanceof  CommonResponse){
            return  o;
        } else {
            response.setData(o);
        }
        return null;
    }
}
