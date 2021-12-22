package com.tjy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tjy.entity.Permission;
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
public interface PermissionMapper extends BaseMapper<Permission> {

	@Select("select t1.permission_id id,t2.url,t2.permission,t2.method from role_permission t1 left JOIN permission t2 on t1.permission_id = t2.id where t1.role_id = #{roleId}")
	List<Permission> listPermissionsByRoles(Integer roleId);
}
