package com.tjy.entity;

import lombok.Data;

/**
 * @Description
 * @Author tjy
 * @Date 2021/12/14 9:46
 * @Version 1.0
 */
@Data
public class Permission {
	private int id;
	private String name;
	private String permission;
	private String url;
	private String method;
}
