package com.example.flashsale.utils;

import java.util.UUID;

public class UUIDUtil {

    public static String getUUID(){
        String uuidStr = UUID.randomUUID().toString();
        uuidStr = uuidStr.replace("-","");
        return uuidStr;
    }
}
