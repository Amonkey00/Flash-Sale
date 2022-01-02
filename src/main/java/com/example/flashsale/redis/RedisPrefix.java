package com.example.flashsale.redis;

public class RedisPrefix extends BasePrefix{

    public RedisPrefix(String prefix, int expireSeconds){
        super(prefix,expireSeconds);
    }

    //User related Prefix
    public static final RedisPrefix USERKEY = new RedisPrefix("userInfo",60);

    //Order related Prefix
    public static final RedisPrefix ORDERKEY = new RedisPrefix("orderInfo",60);

    //Product related Prefix
    public static final RedisPrefix PRODUCTKEY = new RedisPrefix("productInfo",60);

    //Record related Prefix
    public static final RedisPrefix RECORDKEY = new RedisPrefix("recordInfo",60);
}
