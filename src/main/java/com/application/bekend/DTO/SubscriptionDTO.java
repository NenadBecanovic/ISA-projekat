package com.application.bekend.DTO;

import com.application.bekend.model.MyUser;

import javax.persistence.*;

public class SubscriptionDTO {

    private Long id;
    private UserInfoDTO subscribedUser;
    private UserInfoDTO owner;

    public SubscriptionDTO(Long id, UserInfoDTO subscribedUser, UserInfoDTO owner) {
        this.id = id;
        this.subscribedUser = subscribedUser;
        this.owner = owner;
    }

    public SubscriptionDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserInfoDTO getSubscribedUser() {
        return subscribedUser;
    }

    public void setSubscribedUser(UserInfoDTO subscribedUser) {
        this.subscribedUser = subscribedUser;
    }

    public UserInfoDTO getOwner() {
        return owner;
    }

    public void setOwner(UserInfoDTO owner) {
        this.owner = owner;
    }
}
