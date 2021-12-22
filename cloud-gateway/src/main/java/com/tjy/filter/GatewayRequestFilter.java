package com.tjy.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Description
 */
@Component
@Order(0)
public class GatewayRequestFilter implements GlobalFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		byte[] token = Base64Utils.encode("gateway_token_value".getBytes());
		String[] headerValues = {new String(token)};
		ServerHttpRequest build = exchange.getRequest().mutate()
				.header("gateway_token_header", headerValues)
				.build();
		ServerWebExchange newExchange = exchange.mutate().request(build).build();
		return chain.filter(newExchange);
	}
}
