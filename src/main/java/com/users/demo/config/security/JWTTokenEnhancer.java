package com.users.demo.config.security;

import com.users.demo.core.domain.AuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JWTTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        AuthUser authUser = (AuthUser)authentication.getPrincipal();
        authUser.setPassword(authentication.getCredentials().toString());
        Map<String, Object> additionalInfo = new HashMap<>();
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(r -> r.replaceFirst("ROLE_", ""))
                .collect(Collectors.toSet());
        additionalInfo.put("role", roles);
        additionalInfo.put("user_id", authUser.getId());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}