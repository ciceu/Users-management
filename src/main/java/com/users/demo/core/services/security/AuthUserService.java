package com.users.demo.core.services.security;

import com.users.demo.core.domain.AuthUser;
import com.users.demo.core.domain.exceptions.AuthUnauthorizedException;
import com.users.demo.core.domain.exceptions.ErrorStatus;
import com.users.demo.core.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService implements UserDetailsService {

    private static final Logger log = LogManager.getLogger(AuthUserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("load user by username {}", username);
        return userRepository
                .findByEmail(username)
                .map(user -> new AuthUser(user, username))
                .orElseThrow(() -> new AuthUnauthorizedException(ErrorStatus.BAD_CREDENTIALS, String.format("Bad credentials for: %s", username)));
    }
}
