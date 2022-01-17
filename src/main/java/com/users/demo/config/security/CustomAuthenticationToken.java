package com.users.demo.config.security;

import com.users.demo.core.domain.AuthUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import java.util.Collection;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private AuthUser authUser;

    private OAuth2AccessToken accessToken;

    public CustomAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public CustomAuthenticationToken(AuthUser authUser) {
        super(authUser.getAuthorities());
        this.authUser = authUser;
    }

    @Override
    public Object getCredentials() {
        return "NOT_REQUIRED";
    }

    @Override
    public Object getPrincipal() {
        return authUser;
    }

    public OAuth2AccessToken getAccessToken() {return this.accessToken;}

}
