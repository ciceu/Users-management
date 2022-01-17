package com.users.demo.core.services;

import com.users.demo.core.domain.AuthUser;
import com.users.demo.core.domain.User;
import com.users.demo.core.domain.exceptions.AuthUnauthorizedException;
import com.users.demo.core.domain.exceptions.ErrorStatus;
import com.users.demo.core.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LogManager.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("Login attempt");
        AuthUser authUser = userRepository.findByEmail(authentication.getName())
                .map(this::checkIfUserAccountIsConfirmed)
                .map(user -> checkIfUserTypesCorrectPassword(authentication, user))
                .map(User::getAuthUser)
                .orElseThrow(() -> new AuthUnauthorizedException(ErrorStatus.BAD_CREDENTIALS, String.format("Bad credentials for %s", authentication.getName())));

        // Set User Authorities
        return new UsernamePasswordAuthenticationToken(authUser,
                authentication.getCredentials(),
                authUser.getAuthorities());
    }

    private User checkIfUserAccountIsConfirmed(User user) {
        if(!user.isConfirmed())
            throw new AuthUnauthorizedException(ErrorStatus.ACCOUNT_NOT_ENABLED, "Account is not confirmed!");
        return user;
    }

    private User checkIfUserTypesCorrectPassword(Authentication authentication, User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getAuthUser().getPassword()))
            throw new AuthUnauthorizedException(ErrorStatus.BAD_CREDENTIALS, String.format("Bad credentials for %s", authentication.getName()));
        return user;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
