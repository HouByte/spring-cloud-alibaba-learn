package cn.flowboot.e.commerce.service.impl;

import cn.flowboot.e.commerce.service.NacosClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/02
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NacosClientServiceImpl implements NacosClientService {
    private final DiscoveryClient discoveryClient;

    /**
     * <h2> getNacosClientList - 获取服务发现中的客户端<h2>
     * version: 1.0 - 2022/3/2
     * @param
     * @return {@link List< ServiceInstance> }
     */
    @Override
    public List<ServiceInstance> getNacosClientInfo(String serviceId){
        log.info("request nacos client to get service instance info:[{}]",serviceId);
        return discoveryClient.getInstances(serviceId);
    }
}
