//package com.tjy.security.expression;
//
//import org.aopalliance.intercept.MethodInvocation;
//import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
//import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
//import org.springframework.security.authentication.AuthenticationTrustResolver;
//import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
//import org.springframework.security.core.Authentication;
//
///**
// * @Description
// */
///**
// * @author http://www.javadaily.cn
// */
//public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {
//
//	private AuthenticationTrustResolver trustResolver =  new AuthenticationTrustResolverImpl();
//
//	@Override
//	protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
//			Authentication authentication, MethodInvocation invocation) {
//		CustomMethodSecurityExpressionRoot root =
//				new CustomMethodSecurityExpressionRoot(authentication);
//		root.setPermissionEvaluator(getPermissionEvaluator());
//		root.setTrustResolver(this.trustResolver);
//		root.setRoleHierarchy(getRoleHierarchy());
//		return root;
//	}
//}
