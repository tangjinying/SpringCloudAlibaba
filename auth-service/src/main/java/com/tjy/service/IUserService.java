package com.tjy.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tjy.dao.UserMapper;
import com.tjy.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author tjy
 * @Date 2021/12/13 10:53
 * @Version 1.0
 */
@Service
public class IUserService {

    @Resource
    private UserMapper userMapper;

    public User getUserByMobile(String mobile){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getMobile,mobile);
        return userMapper.selectOne(wrapper);
    }

}
