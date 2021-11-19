package com.application.bekend.DTO;

public class ActivationDTO {

    private Long userId;
    private String token;

    public ActivationDTO(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public ActivationDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
