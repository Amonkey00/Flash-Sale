package com.example.flashsale;

import com.example.flashsale.redis.RedisPrefix;
import com.example.flashsale.redis.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@SpringBootTest
class FlashSaleApplicationTests {

    @Autowired
    JedisPool jedisPool;

    @Test
    void contextLoads() {
        RedisService redisService = new RedisService();
        Jedis jedis = jedisPool.getResource();
        //System.out.println(redisService.set(RedisPrefix.USERKEY_ID, "1", 1));
        jedis.close();
    }

}
