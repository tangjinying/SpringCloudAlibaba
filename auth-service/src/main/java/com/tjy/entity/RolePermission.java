package com.tjy.entity;

import lombok.Data;

/**
 * @Description
 * @Author tjy
 * @Date 2021/12/14 9:50
 * @Version 1.0
 */
@Data
public class RolePermission {
	private int id;
	private String roleId;
	private String permissionId;
}
