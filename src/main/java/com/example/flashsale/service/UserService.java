package com.example.flashsale.service;

import com.example.flashsale.dao.UserMapper;
import com.example.flashsale.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    /**
     *  Register method
     */
    public int register(User user){

        // Field Check and filling; UserName and UserPhone cannot be ""(empty string)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStamp = sdf.format(new Date());
        String userName = user.getUname();
        String userPhone = user.getPhone();
        userName = userName.isEmpty() ? ("Default Name" + timeStamp) : userName;
        userPhone = userPhone.isEmpty() ? ("" + timeStamp) : userPhone;
        user.setUname(userName);
        user.setPhone(userPhone);

        // Execute
        return userMapper.addUser(user);
    }


    public int loginWithName(String userName, String password){
        User user = userMapper.getUserByName(userName);
        if(user == null){
            return -1;
        }
        return user.getPassword() == password ? 1 : -1;
    }

    public int loginWithPhone(String phone, String password){
        User user = userMapper.getUserByPhone(phone);
        if(user == null){
            return -1;
        }
        return user.getPassword() == password ? 1 : -1;
    }

    /**
     *  Some utils
     */
    // Utils to getUser
    // Status Code: 1->using Id; 2->using name; 3->using phone
    public User getUser(int statusCode, String param){
        User user = null;
        switch (statusCode){
            case 1: userMapper.getUserById(Integer.valueOf(param));
                break;
            case 2: userMapper.getUserByName(param);
                break;
            case 3: userMapper.getUserByPhone(param);
                break;
        }
        return user;
    }

    public int getUserNumber(){
        return userMapper.countUser();
    }

}
