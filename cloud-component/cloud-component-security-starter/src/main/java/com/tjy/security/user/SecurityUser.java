package com.tjy.security.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @Description
 */
public class SecurityUser extends User {
	@Getter
	private Integer id;

	@Getter
	private String mobile;

	public SecurityUser(Integer id, String mobile,
			String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
		this.mobile = mobile;
	}
}
