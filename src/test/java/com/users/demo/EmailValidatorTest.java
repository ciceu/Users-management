package com.users.demo;

import com.users.demo.core.domain.User;
import com.users.demo.core.domain.exceptions.BadRequestException;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EmailValidatorTest {

    @Test
    public void test_email_validator(){
        User user = new User();
        user.setEmail("test@yahoo.com");
        assertTrue(user.getEmail().equals("test@yahoo.com"));
    }

    @Test(expected = BadRequestException.class)
    public void test_email_validator_wrong_address(){
        User user = new User();
        user.setEmail("test78yahoo.com");
    }
}
