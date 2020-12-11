package xyz.xuanlee.shiro_go.DO.user;

import java.time.LocalDateTime;

abstract public class AbstractUser {
    private Long id;
    private String username;
    private String password;
    private LocalDateTime lastLoginDatetime;

    public AbstractUser() { }

    public AbstractUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AbstractUser(String username, String password, LocalDateTime lastLoginDatetime) {
        this.username = username;
        this.password = password;
        this.lastLoginDatetime = lastLoginDatetime;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getLastLoginDatetime() {
        return lastLoginDatetime;
    }

    public void setLastLoginDatetime(LocalDateTime lastLoginDatetime) {
        this.lastLoginDatetime = lastLoginDatetime;
    }
}
