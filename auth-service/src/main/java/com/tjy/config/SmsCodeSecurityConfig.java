package com.tjy.config;

import com.tjy.auth.sms.SmsCodeAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
public class SmsCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    /**
     * 短信验证码配置器
     *  所有的配置都可以移步到WebSecurityConfig
     *  builder.authenticationProvider() 相当于 auth.authenticationProvider();
     *  使用外部配置必须要在WebSecurityConfig中用http.apply(smsCodeSecurityConfig)将配置注入进去
     * @param builder
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity builder) throws Exception {
        //注入SmsCodeAuthenticationProvider
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        builder.authenticationProvider(smsCodeAuthenticationProvider);
    }
}