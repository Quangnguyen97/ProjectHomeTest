package com.example.hometest.User;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_User")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @NotNull
    @Min(value = 1)
    @Column(name = "user_id")
    private @Id long userId;

    @NotNull
    @NotEmpty(message = "fullName must not be empty")
    @Column(name = "full_name")
    private String fullName;

    @NotNull
    @NotEmpty(message = "passWord must not be empty")
    @Column(name = "pass_word")
    private String passWord;

    @NotNull
    @NotEmpty(message = "notificationToken must not be empty")
    @Column(name = "notification_token")
    private String notificationToken;

    public User(long userId, String fullName, String passWord, String notificationToken) {
        super();
        this.userId = userId;
        this.fullName = fullName;
        this.passWord = passWord;
        this.notificationToken = notificationToken;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof User))
            return false;
        User user = (User) object;
        return Objects.equals(this.userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userId);
    }
}
