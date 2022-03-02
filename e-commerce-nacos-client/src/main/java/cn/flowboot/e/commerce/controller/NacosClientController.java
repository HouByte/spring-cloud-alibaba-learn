package cn.flowboot.e.commerce.controller;

import cn.flowboot.e.commerce.service.NacosClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/02
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RefreshScope
public class NacosClientController {

    private final NacosClientService nacosClientService;

    @GetMapping("/service/instance/{serviceId}")
    public List<ServiceInstance> logNacosClientInfo(@PathVariable("serviceId") String serviceId){
        log.info("request nacos client to get service instance info:[{}]",serviceId);
        return nacosClientService.getNacosClientInfo(serviceId);
    }

    @Value(value = "${flowboot.version}")
    private String version;

    @GetMapping("version")
    public String getVersion(){
        return version;
    }

}
