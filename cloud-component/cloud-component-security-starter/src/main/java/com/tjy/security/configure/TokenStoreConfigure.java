package com.tjy.security.configure;

import com.tjy.security.convert.CustomAccessTokenConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @Description
 */
@Configuration
public class TokenStoreConfigure {

	@Bean
	public JwtAccessTokenConverter jwtTokenEnhancer(){
		JwtAccessTokenConverter jwtTokenEnhancer = new JwtAccessTokenConverter();
		jwtTokenEnhancer.setSigningKey("signKey");
		jwtTokenEnhancer.setAccessTokenConverter(new CustomAccessTokenConverter());
		return jwtTokenEnhancer;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtTokenEnhancer());
	}
}
