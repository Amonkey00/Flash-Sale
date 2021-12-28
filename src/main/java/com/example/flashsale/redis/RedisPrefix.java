package com.example.flashsale.redis;

public class RedisPrefix extends BasePrefix{

    public RedisPrefix(String prefix, int expireSeconds){
        super(prefix,expireSeconds);
    }

    //User related Prefix
    public static RedisPrefix USERKEY_ID = new RedisPrefix("userId",60);
    public static RedisPrefix USERKEY_INFO = new RedisPrefix("userInfo",0);
}
