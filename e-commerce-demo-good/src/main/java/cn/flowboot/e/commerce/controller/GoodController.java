package cn.flowboot.e.commerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/03
 */
@RestController
@RequestMapping("good")
public class GoodController {

    @GetMapping("info")
    public Map<String,Object> info(){
        Map<String,Object> map = new HashMap<>();
        map.put("info","good");
        return map;
    }
}
