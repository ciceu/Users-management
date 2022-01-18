package com.users.demo.core.services;

import com.users.demo.core.domain.AuthUser;
import com.users.demo.core.domain.ConfirmationToken;
import com.users.demo.core.domain.User;
import com.users.demo.core.domain.exceptions.BadRequestException;
import com.users.demo.core.domain.exceptions.ConflictException;
import com.users.demo.core.domain.exceptions.ErrorStatus;
import com.users.demo.core.domain.exceptions.NotFoundException;
import com.users.demo.core.repository.ConfirmationTokenRepository;
import com.users.demo.core.repository.UserRepository;
import com.users.demo.core.services.security.AuthUserService;
import com.users.demo.infrastructure.builders.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Transactional
    @Override
    public User register(String email, String password, String confirmedPassword) {
        log.debug("register a new user with email {}", email);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent())
            throw new ConflictException(ErrorStatus.EMAIL_ADDRESS_ALREADY_EXISTS, "This email address is already used!");
        else {
            AuthUser authUser = createNewAuthUser(email, password, confirmedPassword);
            User newUser = userRepository.save(new User(authUser));
            confirmationTokenRepository.save(new ConfirmationToken(newUser));
            //TODO - sent the confirmation account email
            return newUser;
        }
    }

    @Transactional
    @Override
    public void confirmUserAccount(String confirmationToken) {
        log.debug("confirm user account using token {}", confirmationToken);
        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenRepository.findByToken(confirmationToken);
        if(optionalConfirmationToken.isPresent()) {
            if(optionalConfirmationToken.get().hasExpired())
                throw new BadRequestException(ErrorStatus.LINK_EXPIRED, "The confirmation link has expired!");
            else {
                User user = optionalConfirmationToken.get().getUser();
                user.confirmUserAccount();
                userRepository.save(user);
            }
        }
    }

    @Transactional
    @Override
    public User updateUserById(UserDto userDto, Long userId) {
        log.debug("update user by identifier {}", userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent())
            return optionalUser.get().merge(userDto);
        else
            throw new NotFoundException(ErrorStatus.USER_NOT_FOUND, String.format("Not found user with id: %s", userId));
    }

    private AuthUser createNewAuthUser(String email, String password, String confirmedPassword) {
        log.debug("create a new auth user with email {}", email);
        if(password.equals(confirmedPassword))
            return new AuthUser(email, password);
        else
            throw new BadRequestException(ErrorStatus.INVALID_FIELDS, "Passwords does not matches!");
    }
}
