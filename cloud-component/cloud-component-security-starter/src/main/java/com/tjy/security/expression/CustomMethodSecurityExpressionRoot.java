//package com.tjy.security.expression;
//
///**
// * @Description
// */
//
//import org.springframework.security.access.expression.SecurityExpressionRoot;
//import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.util.AntPathMatcher;
//
//import java.util.Collection;
//
///**
// * 自定义权限校验
// * @author http://www.javadaily.cn
// */
///**
// * 自定义权限校验
// * @author http://www.javadaily.cn
// */
//public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
//	private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
//	public CustomMethodSecurityExpressionRoot(Authentication authentication) {
//		super(authentication);
//	}
//
//	private Object filterObject;
//	private Object returnObject;
//
//	public boolean hasPrivilege(String permission){
//		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//		return authorities.stream()
//				.map(GrantedAuthority::getAuthority)
//				.filter(item -> !item.startsWith("ROLE_"))
//				.anyMatch(x -> antPathMatcher.match(x, permission));
//	}
//
//	@Override
//	public void setFilterObject(Object o) {
//
//	}
//
//	@Override
//	public Object getFilterObject() {
//		return null;
//	}
//
//	@Override
//	public void setReturnObject(Object o) {
//
//	}
//
//	@Override
//	public Object getReturnObject() {
//		return null;
//	}
//
//	@Override
//	public Object getThis() {
//		return null;
//	}
//}
