package com.application.bekend.DTO;

public class AuthUserDTO {

    private String email;
    private String password;

    public AuthUserDTO(String email, String password) {
        this.email = email;
        this.password = password;
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

    public AuthUserDTO() {
    }

    @Override
    public String toString() {
        return "AuthUserDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
