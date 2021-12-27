package com.example.flashsale.redis;

public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;

    private String prefix;

    public BasePrefix(String prefix, int expireSeconds){
        this.prefix = prefix;
        this.expireSeconds = expireSeconds;
    }

    public int expireSeconds() {
        return expireSeconds;
    }

    public String getPrefix() {
        // Prefix = className + prefix
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
