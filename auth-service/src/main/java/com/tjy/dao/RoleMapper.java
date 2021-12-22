package com.tjy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tjy.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/15 18:18
 * @Version 1.0
 */
@Repository
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
	@Select("select t1.role_id id,t2.role_code from user_role t1 left JOIN role t2 on t1.role_id = t2.id where t1.user_id =  #{userId}")
	List<Role> listRolesByUserId(int userId);
}
