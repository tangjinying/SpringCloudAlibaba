package com.tjy.controller;

import com.tjy.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/15 18:29
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserMapper userMapper;


//    @GetMapping("getByName")
//    public User getByName(){
//
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getUsername,"zhangjian");
//        return userMapper.selectOne(wrapper);
//    }

    /**
     * 获取授权的用户信息
     * @param principal 当前用户
     * @return 授权信息
     */
//    @GetMapping("current/get")
    public Principal user(Principal principal){
        return principal;
    }
}
