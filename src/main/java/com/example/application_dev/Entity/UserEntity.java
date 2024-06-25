package com.example.application_dev.Entity;

public class UserEntity {
    public long id;
    private String email;
    private String password;
    private String role;

    public UserEntity() {
    }

    public UserEntity(long id, String email, String password, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, email='%s', password='%s', role='%s']", id, email, password, role);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
