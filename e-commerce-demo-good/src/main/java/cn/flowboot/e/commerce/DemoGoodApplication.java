package cn.flowboot.e.commerce;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/03
 */
@EnableAutoDataSourceProxy
@SpringBootApplication
public class DemoGoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoGoodApplication.class,args);
    }
}
