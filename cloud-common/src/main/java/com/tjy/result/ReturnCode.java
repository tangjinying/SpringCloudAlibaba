package com.tjy.result;

import lombok.Getter;

/**
 * @Description
 */
@Getter
public enum  ReturnCode {
	RC500(500,"服务端异常"),
	UNAUTHORIZED(401,"token无效或无权限访问"),
	CLIENT_AUTHENTICATION_FAILED(1001,"客户端认证失败"),
	USERNAME_OR_PASSWORD_ERROR(1002,"用户名或密码错误"),
	UNSUPPORTED_GRANT_TYPE(1003, "不支持的认证模式");

	private int code;
	private String message;

	ReturnCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
