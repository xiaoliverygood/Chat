package com.example;

import com.example.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;


@SpringBootTest
class ChatApplicationTests {
    @Autowired
    StringRedisTemplate template;//连接redis，并注册为bean

    @Autowired
    UserMapper userMapper;
    @Test
    void test(){
        System.out.println("hello world!");
    }


}
