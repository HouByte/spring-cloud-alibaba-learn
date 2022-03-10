package cn.flowboot.e.commerce.config;

import com.alibaba.cloud.seata.web.SeataHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.sql.DataSource;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/08
 */
//@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * <h2>添加拦截器配置</h2>
     * */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        // Seata 传递 xid 事务 id 给其他的微服务
        // 只有这样, 其他的服务才会写 undo_log, 才能够实现回滚
        registry.addInterceptor(new SeataHandlerInterceptor()).addPathPatterns("/**");
    }

}

