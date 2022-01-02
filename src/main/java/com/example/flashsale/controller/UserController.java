package com.example.flashsale.controller;

import com.example.flashsale.dao.UserMapper;
import com.example.flashsale.pojo.User;
import com.example.flashsale.service.UserService;
import com.example.flashsale.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public JsonResult login (ServletRequest request){
        String userName = request.getParameter("userName");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        int status = 0;
        if(null != userName){
            status = userService.loginWithName(userName, password);
        }
        if(null != phone && status <= 0){
            status = userService.loginWithPhone(phone, password);
        }
        if(status > 0) return new JsonResult();
        return new JsonResult(1, "Operation failed. " +
                "Username/phone not found or password is wrong.");
    }

    @GetMapping("/getUser")
    public JsonResult getUserInfo(int statusCode, String param){
        // StatusCode represents the param's type
        // 1: userID, 2: userName, 3: userPhone
        User user = userService.getUser(statusCode, param);
        if(user == null) return new JsonResult(1, "No user matched.");
        return new JsonResult<User>(user, "Operation succeed.",0);
    }

    @PostMapping("/editUser")
    public JsonResult editUserInfo(ServletRequest request){
        int userId = Integer.parseInt(request.getParameter("userId"));
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");

        User user = userMapper.getUserById(userId);
        if(user == null){
            return new JsonResult(1, "User not exists. Please contact manager.");
        }
        if(null != userName)user.setUname(userName);
        if(null != email)user.setEmail(email);
        int status = userMapper.updateUser(user);
        if(status > 0){
            return new JsonResult();
        }
        return new JsonResult(1, "Update error.");
    }

    @Override
    public String toString() {
        return "UserController{" +
                "userService=" + userService +
                ", userMapper=" + userMapper +
                '}';
    }
}
