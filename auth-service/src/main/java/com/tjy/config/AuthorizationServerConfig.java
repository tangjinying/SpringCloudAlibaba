package com.tjy.config;

import com.tjy.auth.UserDetailServiceImpl;
import com.tjy.auth.exception.CustomClientCredentialsTokenEndpointFilter;
import com.tjy.auth.exception.CustomWebResponseExceptionTranslator;
import com.tjy.jwt.CustomJwtTokenConverter;
import com.tjy.result.ResultData;
import com.tjy.result.ReturnCode;
import com.tjy.util.WebUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.sql.DataSource;

/**
 * @Description 授权/认证服务器配置
 * @Author tjy
 * @Date 2021/11/15 18:04
 * @Version 1.0
 */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    // 认证管理器
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TokenGranter tokenGranter;

    /**
     * access_token存储器
     * 这里存储在数据库，大家可以结合自己的业务场景考虑将access_token存入数据库还是redis
     */
    @Bean
    public TokenStore tokenStore() {
//        return new JdbcTokenStore(dataSource);
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    /**
     * JwtAccessTokenConverter
     * TokenEnhancer的子类，帮助程序在JWT编码的令牌值和OAuth身份验证信息之间进行转换。
     */
    @Bean
    public JwtAccessTokenConverter jwtTokenEnhancer(){
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        JwtAccessTokenConverter converter = new CustomJwtTokenConverter();
        // 设置对称签名
        converter.setSigningKey("signKey");
        return converter;
    }




    /**
     * 从数据库读取clientDetails相关配置
     * 有InMemoryClientDetailsService 和 JdbcClientDetailsService 两种方式选择
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 注入密码加密实现器
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return (request, response, e) -> {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			ResultData<String> resultData = ResultData.fail(ReturnCode.CLIENT_AUTHENTICATION_FAILED.getCode(), ReturnCode.CLIENT_AUTHENTICATION_FAILED.getMessage());
			WebUtils.writeJson(response,resultData);
		};
	}



	/**
     * 认证服务器Endpoints配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //如果需要使用refresh_token模式则需要注入userDetailService
        endpoints
                .authenticationManager(this.authenticationManager)
                .userDetailsService(userDetailService)
        		.tokenGranter(tokenGranter);
//                .tokenStore(tokenStore())
//                .accessTokenConverter(jwtTokenEnhancer())
//                //注入自定义的tokenService
//                .tokenServices(tokenServices());
		endpoints.exceptionTranslator(new CustomWebResponseExceptionTranslator());
    }

    /**
     * 认证服务器相关接口权限管理
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		CustomClientCredentialsTokenEndpointFilter endpointFilter = new CustomClientCredentialsTokenEndpointFilter(security);
		endpointFilter.afterPropertiesSet();
		endpointFilter.setAuthenticationEntryPoint(authenticationEntryPoint());
		security.addTokenEndpointAuthenticationFilter(endpointFilter);

        security
				.authenticationEntryPoint(authenticationEntryPoint())
//				.allowFormAuthenticationForClients() //如果使用表单认证则需要加上
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * client存储方式，此处使用jdbc存储
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails());
    }

}
