//package com.tjy.auth;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.authorization.AuthorizationDecision;
//import org.springframework.security.authorization.ReactiveAuthorizationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.web.server.authorization.AuthorizationContext;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * @Description 权限管理器
// * @Author tjy
// * @Date 2021/11/16 11:31
// * @Version 1.0
// */
//@Slf4j
//@Component
//public class AccessManager implements ReactiveAuthorizationManager<AuthorizationContext> {
//
//    private Set<String> permitAll = new HashSet<>();
//    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
//
//
//    public AccessManager (){
//        permitAll.add("/");
//        permitAll.add("/error");
//        permitAll.add("/favicon.ico");
//        permitAll.add("/**/v2/api-docs/**");
//        permitAll.add("/**/swagger-resources/**");
//        permitAll.add("/webjars/**");
//        permitAll.add("/doc.html");
//        permitAll.add("/swagger-ui.html");
//        permitAll.add("/**/oauth/**");
//        permitAll.add("/**/current/get");
//    }
//
//    /**
//     * 实现权限验证判断
//     */
//    @Override
//    public Mono<AuthorizationDecision> check(Mono<Authentication> authenticationMono, AuthorizationContext authorizationContext) {
//        ServerWebExchange exchange = authorizationContext.getExchange();
//		ServerHttpRequest request = exchange.getRequest();
//        //请求资源
//        String requestPath = request.getURI().getPath();
//        //拼接method
//		String methodPath = "["+request.getMethod()+"]" + requestPath;
//
//		// 对应跨域的预检请求直接放行
//		if(request.getMethod() == HttpMethod.OPTIONS){
//			return Mono.just(new AuthorizationDecision(true));
//		}
//
//        // 是否直接放行
//        if (permitAll(requestPath)) {
//            return Mono.just(new AuthorizationDecision(true));
//        }
//
//        return authenticationMono.map(auth ->
//             new AuthorizationDecision(checkAuthorities(auth, methodPath))
//        ).defaultIfEmpty(new AuthorizationDecision(false));
//
//    }
//
//    /**
//     * 校验是否属于静态资源
//     * @param requestPath 请求路径
//     * @return
//     */
//    private boolean permitAll(String requestPath) {
//        return permitAll.stream()
//                .filter(r -> antPathMatcher.match(r, requestPath)).findFirst().isPresent();
//    }
//
//	/**
//	 * 校验权限
//	 * @param auth 用户权限
//	 * @param requestPath 请求路径
//	 * @return
//	 */
//    private boolean checkAuthorities( Authentication auth, String requestPath) {
//        if(auth instanceof OAuth2Authentication){
//			Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
//
//			return authorities.stream()
//					.map(GrantedAuthority::getAuthority)
//					.filter(item -> !item.startsWith("ROLE_"))
//					.anyMatch(permission -> antPathMatcher.match(permission,requestPath));
//        }
//       	return false;
//    }
//
//}
