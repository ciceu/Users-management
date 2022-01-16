package com.users.demo.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@Slf4j
@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken extends BaseEntity {

    private static final int EXPIRATION = 60 * 24; /** 24 hours **/
    private static final String DIGITS_TEXT = "0123456789";
    private static final String LETTERS_TEXT = "abcdefghijklmnopqrstuvwxyz";
    private static final int DIGITS_LENGTH = 3;
    private static final int LETTERS_LENGTH = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "token")
    private String token;

    @Getter
    @OneToOne(targetEntity = User.class)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Getter
    @Setter
    @Column(name = "expire_at")
    private LocalDateTime expiryDate;

    public ConfirmationToken(final User user) {
        super();
        this.token = generateCode();
        this.user = user;
        this.expiryDate = calculateExpiryDate();
    }

    private LocalDateTime calculateExpiryDate() {
        return LocalDateTime
                .now()
                .plusMinutes(ConfirmationToken.EXPIRATION);
    }

    private String generateCode(){
        log.debug("generate random code with {} digits and {} letters", DIGITS_LENGTH, LETTERS_LENGTH);
        SecureRandom random = new SecureRandom();
        List<Character> code = new ArrayList<>();
        for (int i = 0; i < DIGITS_LENGTH; i++)
            code.add(DIGITS_TEXT.charAt(random.nextInt(DIGITS_TEXT.length())));
        for (int i = 0; i < LETTERS_LENGTH; i++)
            code.add(LETTERS_TEXT.charAt(random.nextInt(LETTERS_TEXT.length())));
        Collections.shuffle(code);
        StringBuilder stringCode = new StringBuilder(DIGITS_LENGTH + LETTERS_LENGTH);
        code.forEach(stringCode::append);
        return stringCode.toString();
    }

    public boolean hasExpired(){
        return LocalDateTime.now().isAfter(this.expiryDate);
    }

    public ConfirmationToken updateExpiryDate() {
        this.expiryDate = calculateExpiryDate();
        return this;
    }
}
