package com.suneoda.springbootredis.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Description:
 *
 * @author shiguorang
 * @date 2021-04-21 16:06:44
 */
@RestController
@RequestMapping("stringRedis")
@Slf4j
public class StringRedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @Author shiguorang
     * @Description //操作string
     * @Date 16:33:04 2021-04-21
     * @Param
     * @return
     **/
    @GetMapping("/getString")
    public void getString() {
        stringRedisTemplate.opsForValue().set("name", "shiguorang");
        String name = stringRedisTemplate.opsForValue().get("name");
        log.info("获取到的name："+name);
    }

    /**
     * @Author shiguorang
     * @Description //操作键
     * @Date 16:33:17 2021-04-21
     * @Param
     * @return
     **/
    @GetMapping("/key")
    public void key(){
        Set<String> name = stringRedisTemplate.keys("list");
        name.forEach(one -> {
            log.info("获取到的值："+one);
        });

        Boolean key = stringRedisTemplate.hasKey("name");
        log.info("键name："+key);
    }

    /**
     * @Author shiguorang
     * @Description //操作list
     * @Date 16:33:31 2021-04-21
     * @Param
     * @return
     **/
    @GetMapping("/opsList")
    public void OpsList(){
        Long aLong = stringRedisTemplate.opsForList().leftPush("man", "shiguorang");
        log.info("push:"+aLong);
        List<String> man = stringRedisTemplate.opsForList().range("man", 0, -1);
        man.forEach(one -> {
            log.info("list值："+one);
        });
    }

    /**
     * @Author shiguorang
     * @Description //操作set
     * @Date 16:33:42 2021-04-21
     * @Param
     * @return
     **/
    @GetMapping("/opsSet")
    public void opsSet(){
        Long sets = stringRedisTemplate.opsForSet().add("sets", "1", "2", "3");
        log.info("插入的set长度为："+sets);
    }

    /**
     * @Author shiguorang
     * @Description //操作hash
     * @Date 16:41:33 2021-04-21
     * @Param
     * @return
     **/
    @GetMapping("/opsHash")
    public void opsHash(){
        stringRedisTemplate.opsForHash().put("map","name","shiguorang");
        Object o = stringRedisTemplate.opsForHash().get("map", "name");
        log.info("map:"+o);
    }
}
