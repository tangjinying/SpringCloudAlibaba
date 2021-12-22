package com.tjy.security.convert;

import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

    public CustomAccessTokenConverter(){
        super.setUserTokenConverter(new CustomUserAuthenticationConverter());
    }
}