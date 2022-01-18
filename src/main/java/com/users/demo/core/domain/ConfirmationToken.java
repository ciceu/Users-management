package com.users.demo.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
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
    @Column(name = "token", unique = true)
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
        this.token = UUID.randomUUID().toString();
        this.user = user;
        this.expiryDate = calculateExpiryDate();
    }

    private LocalDateTime calculateExpiryDate() {
        return LocalDateTime
                .now()
                .plusMinutes(ConfirmationToken.EXPIRATION);
    }

    public boolean hasExpired(){
        return LocalDateTime.now().isAfter(this.expiryDate);
    }
}
