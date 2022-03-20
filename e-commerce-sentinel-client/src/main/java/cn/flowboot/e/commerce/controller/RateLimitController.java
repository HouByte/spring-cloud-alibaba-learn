package cn.flowboot.e.commerce.controller;

import cn.flowboot.e.commerce.block.handler.MyBlockHandler;
import cn.flowboot.e.commerce.vo.CommonResponse;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>基于Sentinel 控制台配置流控规则</h1>
 * Sentinel 是懒加载的，先去访问一下，就可以在 Sentinel Dashboard看到了
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/17
 */
@Slf4j
@RestController
@RequestMapping("/rate")
public class RateLimitController {



    /**
     * <h2>在 dashboard 中“流控规则”中按照资源名称新增流控规则</h2>
     * @return
     */
    @GetMapping("/by-resource")
    @SentinelResource(value = "byResource",blockHandler = "myHandleException",blockHandlerClass = MyBlockHandler.class)
    public CommonResponse<String> byResource(){
        log.info("coming in rate limit controller by resource");
        return CommonResponse.success();
    }


    /**
     * <h2>在 "触点链路" 中给URL添加流控规则</h2>
     * @return
     */
    @GetMapping("/by-url")
    @SentinelResource(value = "byUrl")
    public CommonResponse<String> byUrl(){
        log.info("coming in rate limit controller by Url");
        return CommonResponse.success("byUrl");
    }
}
