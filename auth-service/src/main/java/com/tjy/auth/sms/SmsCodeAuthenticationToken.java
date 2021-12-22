package com.tjy.auth.sms;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Description
 * @Author tjy
 * @Date 2021/12/13 10:40
 * @Version 1.0
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 7325920977846873841L;
    /**
     * 账号主体信息，手机号验证码登录体系中代表 手机号码
     */
    private final Object principal;


    /**
     * 构建未授权的 SmsCodeAuthenticationToken
     * @param mobile 手机号码
     */
    public SmsCodeAuthenticationToken(String mobile) {
        super(null);
        this.principal = mobile;
        setAuthenticated(false);
    }


    /**
     * 构建已经授权的 SmsCodeAuthenticationToken
     */
    public SmsCodeAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities){
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }


    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        if(isAuthenticated){
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }else{
            super.setAuthenticated(false);
        }
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
