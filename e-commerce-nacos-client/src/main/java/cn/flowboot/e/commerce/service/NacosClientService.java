package cn.flowboot.e.commerce.service;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * <h1>客户端接口</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/02
 */
public interface NacosClientService {

    /**
     * <h2> getNacosClientList - 获取服务发现中的客户端<h2>
     * version: 1.0 - 2022/3/2
     * @param
     * @return {@link List< ServiceInstance> }
     */
    List<ServiceInstance> getNacosClientInfo(String serviceId);
}
