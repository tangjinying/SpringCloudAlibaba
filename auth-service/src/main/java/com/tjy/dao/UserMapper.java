package com.tjy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tjy.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/15 18:18
 * @Version 1.0
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
