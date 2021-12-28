package com.example.flashsale;

import com.example.flashsale.pojo.User;
import com.example.flashsale.redis.RedisPrefix;
import com.example.flashsale.redis.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@SpringBootTest
class FlashSaleApplicationTests {

    @Autowired
    RedisService redisService;

    @Test
    void contextLoads() {

    }

}
