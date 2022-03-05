package cn.flowboot.e.commerce.config;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/05
 */
@Configuration
public class FeignConfiguration {

    /**
     * <h2> feignLoggerLevel - 日志级别 <h2>
     * version: 1.0 - 2022/3/5
     * @param
     * @return {@link Logger.Level }
     */
    @Bean
    public Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

    /**
     * <h2> feignRetryer - OpenFeign 开启重试 <h2>
     * period = 100 发起时间间隔，单位是ms
     * maxPeriod = 1000发起当前请求的最大时间间隔,单位是 ms
     * maxAttrmpts = 5 最多请求次数
     * version: 1.0 - 2022/3/5
     * @param
     * @return {@link Retryer }
     */
    @Bean
    public Retryer feignRetryer(){
        return new Retryer.Default(
                100,
                SECONDS.toMillis(1),
                5
        );
    }

    public static final  int CONNECT_TIMEOUT_MILLS = 5000;
    public static final  int READ_TIMEOUT_MILLS = 5000;


    /**
     * <h2> options - 对请求的连接和响应时间进行限制<h2>
     * version: 1.0 - 2022/3/5
     * @param
     * @return {@link Request.Options }
     */
    @Bean
    public Request.Options options(){
        return new Request.Options(
                CONNECT_TIMEOUT_MILLS, TimeUnit.MILLISECONDS,READ_TIMEOUT_MILLS,TimeUnit.MILLISECONDS,true
        );
    }
}
