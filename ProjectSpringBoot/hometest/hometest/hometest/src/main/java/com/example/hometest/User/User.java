package com.example.hometest.User;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @NotNull
    @Min(value = 1)
    private @Id @GeneratedValue(strategy = GenerationType.AUTO) long userId;

    @NotNull
    @NotEmpty(message = "fullName must not be empty")
    private String fullName;

    @NotNull
    @NotEmpty(message = "password must not be empty")
    private String password;

    @NotNull
    @NotEmpty(message = "notificationToken must not be empty")
    private String notificationToken;

    public User(long userId, String fullName, String password, String notificationToken) {
        super();
        this.userId = userId;
        this.fullName = fullName;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
