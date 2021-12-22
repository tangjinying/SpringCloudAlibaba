package com.tjy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/15 18:17
 * @Version 1.0
 */
@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String mobile;

    @TableField(exist = false)
    private List<String> roles;
	@TableField(exist = false)
    private List<String> permissions;
}
