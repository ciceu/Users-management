package com.users.demo.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "birthdate")
    private LocalDateTime birthdate;

    @Getter
    @OneToOne(targetEntity = AuthUser.class)
    @JoinColumn(nullable = false, name = "auth_user_id")
    private AuthUser authUser;

    public User (AuthUser authUser) {
        this.email = authUser.getUsername();
        this.authUser = authUser;
    }

}
