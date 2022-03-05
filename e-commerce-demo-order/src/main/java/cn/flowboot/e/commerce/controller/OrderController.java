package cn.flowboot.e.commerce.controller;

import cn.flowboot.e.commerce.dto.Good;
import cn.flowboot.e.commerce.dto.SearchGoodByIdsDto;
import cn.flowboot.e.commerce.feign.GoodFeignClient;
import cn.flowboot.e.commerce.vo.CommonResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/03
 */
@Slf4j
@RestController
@RequestMapping("order")
public class OrderController {

    @GetMapping("info")
    public Map<String,Object> info(){
        Map<String,Object> map = new HashMap<>();
        map.put("info","good");
        return map;
    }


    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/create/order")
    public Map<String,Object> createOrder(@RequestParam(defaultValue = "1,2,3") List<Integer> ids){


        //第一种方式:写死url
        //String requestUrL = "http://127.0.0.1:8800/demo-good/good/search/list";
        String requestUrL = "http://e-commerce-demo-good/demo-good/good/search/list";

        log.info("RestTemplate request url [{}] and body [{}]:",requestUrL,ids);

        SearchGoodByIdsDto searchGoodByIdsDto = new SearchGoodByIdsDto();
        searchGoodByIdsDto.setIds(ids);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CommonResponse response = restTemplate.postForObject(requestUrL, new HttpEntity<>(JSON.toJSONString(searchGoodByIdsDto), headers), CommonResponse.class);
        Map<String,Object> map = new HashMap<>();
        map.put("result",response.getData());
        return map;
    }

    @Autowired
    private GoodFeignClient goodFeignClient;

    @GetMapping("/feign/create/order")
    public Map<String,Object> createOrderByFeign(@RequestParam(defaultValue = "1,2,3") List<Integer> ids){

        log.info("GoodFeignClient request  body [{}]:",ids);

        SearchGoodByIdsDto searchGoodByIdsDto = new SearchGoodByIdsDto();
        searchGoodByIdsDto.setIds(ids);

        CommonResponse response = goodFeignClient.list(searchGoodByIdsDto);
        Map<String,Object> map = new HashMap<>();
        map.put("result",response.getData() );
        return map;
    }
}
