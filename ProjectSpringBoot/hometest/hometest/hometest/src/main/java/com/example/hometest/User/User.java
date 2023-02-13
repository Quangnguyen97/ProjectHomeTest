package com.example.hometest.User;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {
    private @Id @GeneratedValue long UserId;
    private String FullName;
    private String Password;
    private String NotificationToken;

    public User(long UserId, String FullName, String Password, String NotificationToken) {
        super();
        this.UserId = UserId;
        this.FullName = FullName;
        this.Password = Password;
        this.NotificationToken = NotificationToken;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long UserId) {
        this.UserId = UserId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getNotificationToken() {
        return NotificationToken;
    }

    public void setNotificationToken(String NotificationToken) {
        this.NotificationToken = NotificationToken;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof User))
            return false;
        User user = (User) object;
        return Objects.equals(this.UserId, user.UserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.UserId);
    }
}
