package com.example.flashsale.controller;

import com.example.flashsale.dao.UserMapper;
import com.example.flashsale.pojo.User;
import com.example.flashsale.service.UserService;
import com.example.flashsale.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.io.Serializable;

@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @PostMapping("/register")
    public JsonResult register(ServletRequest request){
        String userName = request.getParameter("userName");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        Double wallet = 0d;
        User user = new User(userName,phone,password,email,wallet);
        System.out.println(user);

        // Check existing user
        boolean userNameCheck = userMapper.getUserByName(user.getUname()) == null;
        boolean userPhoneCheck = userMapper.getUserByPhone(user.getPhone()) == null;
        // If User exists
        if(!userNameCheck) return new JsonResult(1, "Username exists");
        if(!userPhoneCheck) return new JsonResult(1, "Phone number exists");

        // Execute
        int status = userService.register(user);
        if(status > 0){
            return new JsonResult();
        }
        return new JsonResult(1, "Operation Failed.");
    }

    @PostMapping("/login")
    public String login (ServletRequest request){
        String userName = request.getParameter("userName");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        int status = 0;
        if(!userName.isEmpty()){
            status = userService.loginWithName(userName, password);
        }
        if(!phone.isEmpty() && status <= 0){
            status = userService.loginWithPhone(phone, password);
        }
        if(status > 0) return "Success";
        return "Failed";
    }

    @RequestMapping("/getUser")
    public String getUserInfo(int statusCode, String param){
        // StatusCode represents the param's type
        // 1: userID, 2: userName, 3: userPhone
        User user = userService.getUser(statusCode, param);
        if(user == null) return "Failed";
        return user.toString();
    }

    @Override
    public String toString() {
        return "UserController{" +
                "userService=" + userService +
                ", userMapper=" + userMapper +
                '}';
    }
}
