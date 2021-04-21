package com.suneoda.springbootredis.controller;

import com.suneoda.springbootredis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description:
 *
 * @author shiguorang
 * @date 2021-04-21 18:35:04
 */
@RestController
@Slf4j
@RequestMapping("redisTemplate")
public class RedisTemplateController {


    /**
     * RedisTemplate 在操作redis的时候会将对象进行序列化
     * 在读取的时候会将对象进行反序列化
     * 在代码中对redis进行新增键，在redis客户端实无法用代码中的键进行获取键值的，
     * 因为键值都会被序列化
     *
     * 在我们期望键是string的时候，我们可以对键的序列化方案进行更改，
     * 我们也可以对值得序列化方案进行更改，但是没必要
     *
     * 键值默认都是jdk的序列化方案
     */

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @Author shiguorang
     * @Description //操作string
     * @Date 19:12:57 2021-04-21
     * @Param
     * @return
     **/
    @GetMapping("/opsString")
    public void opsString(){

//      更改键的序列化方案
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        更改值得序列化方案
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        更改hash键的序列化方案
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        User user = new User();
        user.setName("shiguorang");
        user.setAge(24);
        user.setSex("男");
        redisTemplate.opsForValue().set("name", user);
        User name = (User) redisTemplate.opsForValue().get("name");
        log.info("name:"+name);
    }

    /**
     * @Author shiguorang
     * @Description //操作list
     * @Date 19:13:10 2021-04-21
     * @Param
     * @return
     **/
    @GetMapping("/opsList")
    public void opsList(){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        User user = new User();
        user.setName("shiguorang");
        user.setAge(24);
        user.setSex("男");
        User user2 = new User();
        user2.setName("ming");
        user2.setAge(21);
        user2.setSex("男");
        redisTemplate.opsForList().leftPushAll("number", user, user2);

        List<User> number = redisTemplate.opsForList().range("number", 0, -1);
        number.forEach(one -> {
            log.info("值："+one);
        });
    }

    /**
     * @Author shiguorang
     * @Description //操作hash
     * @Date 19:13:24 2021-04-21
     * @Param
     * @return
     **/
    @GetMapping("/opsHash")
    public void opsHash(){
//        更改键的序列化方案
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        更改hash键的序列化方案
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        Map<String, User> map = new HashMap<>();
        User user = new User();
        user.setName("shiguorang");
        user.setAge(24);
        user.setSex("男");
        map.put("shiguorang",user);
        User user2 = new User();
        user2.setName("ming");
        user2.setAge(21);
        user2.setSex("男");
        map.put("ming", user2);
        redisTemplate.opsForHash().putAll("map", map);
        User getUser = (User) redisTemplate.opsForHash().get("map", "shiguorang");
        log.info("获取到的值"+getUser);
    }

    @GetMapping("/opsSet")
    public void opsSet(){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        User user = new User();
        user.setName("shiguorang");
        user.setAge(24);
        user.setSex("男");

        User user2 = new User();
        user2.setName("ming");
        user2.setAge(21);
        user2.setSex("男");

        redisTemplate.opsForSet().add("sets", user, user2);
        Set<User> sets = redisTemplate.opsForSet().members("sets");
        sets.forEach(one -> {
            log.info("获取到的值"+one);
        });
    }

    /**
     * @Author shiguorang
     * @Description //绑定操作
     * @Date 20:12:08 2021-04-21
     * @Param
     * @return
     **/
    @GetMapping("/bound")
    public void bound(){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps("name");
        User user = new User();
        user.setName("shiguorang");
        user.setAge(24);
        user.setSex("男");
        boundValueOperations.set(user);

        User get = (User) boundValueOperations.get();
        log.info("获取到的值"+get);
    }

}
