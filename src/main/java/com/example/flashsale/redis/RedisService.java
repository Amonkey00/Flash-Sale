package com.example.flashsale.redis;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    /**
     * Get Key
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            // Generate Real Key = prefix + key_name
            String realKey = prefix.getPrefix() + key;
            String resStr = jedis.get(realKey);
            T t = null;
            //T t = stringToBean(resStr, clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean set(KeyPrefix prefix, String key, T value){
        Jedis jedis = null;
        try{
            System.out.println(jedisPool);
            jedis = jedisPool.getResource();
            String setStr = beanToString(value);
            if(setStr == null || setStr.length() <= 0){
                return false;
            }
            // Generate real key
            String realKey = prefix.getPrefix() + key;
            int seconds = prefix.expireSeconds();
            // Non-limited Expire time
            if(seconds <= 0){
                jedis.set(realKey, setStr);
            }
            // limited Expire time
            else{
                jedis.setex(realKey, seconds, setStr);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * Check the key exists
     */
    public boolean exists(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * Delete key
     */
    public boolean delete(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            long status = jedis.del(realKey);
            return status > 0;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * Incr by
     */
    public Long incr(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * Decr by
     */
    public Long decr(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * Transfer str to Bean Object
     */
    public static <T> T stringToBean(String str, Class<T> clazz){
        if(str == null || str.length() <=0 || clazz == null){
            return null;
        }
        if(clazz == int.class || clazz == Integer.class){
            return (T) Integer.valueOf(str);
        }
        else if(clazz == long.class || clazz == Long.class){
            return (T) Long.valueOf(str);
        }
        else if(clazz == String.class){
            return (T) str;
        }
        return JSON.toJavaObject(JSON.parseObject(str), clazz);
    }

    /**
     * Transfer bean object to String
     */
    public static <T> String beanToString(T value){
        if(value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class){
            return String.valueOf(value);
        }
        else if(clazz == long.class || clazz == Long.class){
            return String.valueOf(value);
        }
        else if(clazz == String.class){
            return (String) value;
        }
        return JSON.toJSONString(value);
    }

    /**
     * delete all key-value pairs of certain prefix
     */
    public boolean delete(KeyPrefix prefix){
        if(prefix == null){
            return false;
        }
        List<String> keys = scanKeys(prefix.getPrefix());
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.del(keys.toArray(new String[0]));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * get all key names of certain prefix
     */
    public List<String> scanKeys(String prefixKey){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            List<String> keys = new ArrayList<>();
            String cursor = "0";

            // Use ScanParams to match keys
            ScanParams sp = new ScanParams();
            sp.match("*" + prefixKey + "*");
            sp.count(100);
            do{
                ScanResult<String> ret = jedis.scan(cursor, sp);
                List<String> result = ret.getResult();
                if(result != null && result.size() > 0){
                    keys.addAll(result);
                }
                cursor = ret.getCursor();
            }while(!cursor.equals("0"));
            return keys;

        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * Close the jedis conn
     */
    private void returnToPool(Jedis jedis){
        if(null != jedis){
            jedis.close();
        }
    }
}
