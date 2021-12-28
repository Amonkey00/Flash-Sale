package com.example.flashsale.dao;

import com.example.flashsale.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    // GET
    public User getUserById(@Param("userId")int id);
    public User getUserByName(@Param("userName")String name);
    public User getUserByPhone(@Param("userPhone")String phone);
    public User getUser(@Param("userName")String name, @Param("userPhone")String phone);
    public int countUser();

    // INSERT
    public int addUser(User user);

    // UPDATE
    public int updateUser(User user);

}
