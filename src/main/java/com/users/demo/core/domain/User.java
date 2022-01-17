package com.users.demo.core.domain;

import com.users.demo.infrastructure.builders.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
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

    @Getter
    @Column(name = "username")
    private String username;

    @Getter
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Getter
    @Column(name = "first_name")
    private String firstName;

    @Getter
    @Column(name = "last_name")
    private String lastName;

    @Getter
    @Column(name = "phone_number")
    private String phoneNumber;

    @Getter
    @Column(name = "photo_url")
    private String photoUrl;

    @Getter
    @Column(name = "birthdate")
    private LocalDateTime birthdate;

    @Column(name = "confirmed")
    private boolean confirmed;

    @Getter
    @OneToOne(targetEntity = AuthUser.class, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "auth_user_id")
    private AuthUser authUser;

    public User (AuthUser authUser) {
        this.email = authUser.getUsername();
        this.authUser = authUser;
    }

    public User merge(UserDto userDto) {
        this.username = userDto.getUsername();
        this.phoneNumber = userDto.getPhoneNumber();
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.photoUrl = userDto.getPhotoUrl();
        return this;
    }

    public boolean isConfirmed() {
        return this.confirmed;
    }

    public void confirmUserAccount(){
        this.confirmed = true;
        this.authUser.enableUser();
    }
}
