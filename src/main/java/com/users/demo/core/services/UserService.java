package com.users.demo.core.services;

import com.users.demo.core.domain.User;
import com.users.demo.infrastructure.builders.UserDto;

/**
 * The userService interface implements
 * @author  Ciceu Crina
 * @since   2022-01-17
 */
public interface UserService {
    /**
     *
     * @param email the email which will be used for create a new user
     * @param password the password used for the new account
     * @param  confirmedPassword the password value
     * @return the new user
     * Method used to register a new user in the system
     */
    User register(String email, String password, String confirmedPassword);
    /**
     *
     * @param  confirmationToken the token used to confirm user account
     * Method used to confirm a new user account
     */
    void confirmUserAccount(String confirmationToken);
}
