package com.example.flashsale.service;

import com.example.flashsale.dao.UserMapper;
import com.example.flashsale.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserService {

    public static final int IDKEY = 1;
    public static final int NAMEKEY = 2;
    public static final int PHONEKEY = 3;

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
        return user.getPassword().equals(password) ? 1 : -1;
    }

    public int loginWithPhone(String phone, String password){
        User user = userMapper.getUserByPhone(phone);
        if(user == null){
            return -1;
        }
        return user.getPassword().equals(password) ? 1 : -1;
    }

    /**
     * Modify the wallet of user.
     * @param userId The user be modified
     * @param money The number of money [+:add money, -: decline money]
     * @return result
     */
    @Transactional(rollbackFor = Exception.class)
    public int modifyWallet(int userId, Double money){
        User user = userMapper.getUserById(userId);
        Double expectWallet = user.getWallet() + money;
        if(expectWallet < 0) return -1;
        user.setWallet(expectWallet);
        return userMapper.updateUser(user);
    }

    /**
     *  Some utils
     */
    // Utils to getUser
    // Status Code: 1->using Id; 2->using name; 3->using phone
    public User getUser(int statusCode, String param){
        User user = null;
        switch (statusCode){
            case IDKEY: user = userMapper.getUserById(Integer.valueOf(param));
                break;
            case NAMEKEY: user = userMapper.getUserByName(param);
                break;
            case PHONEKEY: user = userMapper.getUserByPhone(param);
                break;
        }
        return user;
    }

    public int getUserNumber(){
        return userMapper.countUser();
    }

}
