package com.users.demo;

import com.users.demo.core.domain.AuthUser;
import com.users.demo.core.domain.ConfirmationToken;
import com.users.demo.core.domain.User;
import com.users.demo.core.domain.exceptions.BadRequestException;
import com.users.demo.core.repository.ConfirmationTokenRepository;
import com.users.demo.core.repository.UserRepository;
import com.users.demo.core.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ConfirmUserAccountTest {

        @Mock
        private ConfirmationTokenRepository confirmationTokenRepository;
        @Mock
        private UserRepository userRepository;
        @InjectMocks
        private UserServiceImpl userService;
        private User user;
        private AuthUser authUser;
        private ConfirmationToken confirmationToken;

        @Before
        public void setUp() {
            //Given an account and a confirmation token
            authUser = AuthUser.builder()
                    .username("test@yahoo.com")
                    .password("$2a$10$00qijgdJZj6DnFDkQuVt5.l8ruMjeurMD18zwrtwzNkmGJfe/1qwu")
                    .build();
            user = User.builder()
                    .id(1L)
                    .confirmed(false)
                    .email("test@yahoo.com")
                    .authUser(authUser)
                    .build();
            confirmationToken = new ConfirmationToken(user);
        }

        @Test
        public void test_confirmUserAccountExpiredToken() {
            //When the token has expired
            confirmationToken.setExpiryDate(LocalDateTime.now().minusMonths(1));
            Mockito.when(confirmationTokenRepository.findByToken(confirmationToken.getToken())).thenReturn(java.util.Optional.of(confirmationToken));
            //Then the method throws an exception
            assertThrows(BadRequestException.class, () -> userService.confirmUserAccount(confirmationToken.getToken()));
        }


        @Test
        public void test_confirmUserAccount() {
            //When the token is present
            Mockito.when(confirmationTokenRepository.findByToken(confirmationToken.getToken())).thenReturn(Optional.of(confirmationToken));
            userService.confirmUserAccount(confirmationToken.getToken());
            //The account will be confirmed
            assertTrue(confirmationToken.getUser().isConfirmed());
        }
}
