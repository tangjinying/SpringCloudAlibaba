package com.tjy.jwt;

import com.tjy.auth.SecurityUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 */
public class CustomJwtTokenConverter extends JwtAccessTokenConverter {
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		SecurityUser securityUser = (SecurityUser)authentication.getUserAuthentication().getPrincipal();
		final Map<String,Object> additionalInformation = new HashMap<>(4);
		additionalInformation.put("userId",securityUser.getId());
		additionalInformation.put("mobile",securityUser.getMobile());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
		return super.enhance(accessToken, authentication);
	}
}
