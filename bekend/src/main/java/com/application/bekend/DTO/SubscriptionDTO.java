package com.application.bekend.DTO;

import com.application.bekend.model.MyUser;

import javax.persistence.*;

public class SubscriptionDTO {

    private Long id;
    private MyUserDTO subscribedUser;
    private MyUserDTO owner;

    public SubscriptionDTO(Long id, MyUserDTO subscribedUser, MyUserDTO owner) {
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

    public MyUserDTO getSubscribedUser() {
        return subscribedUser;
    }

    public void setSubscribedUser(MyUserDTO subscribedUser) {
        this.subscribedUser = subscribedUser;
    }

    public MyUserDTO getOwner() {
        return owner;
    }

    public void setOwner(MyUserDTO owner) {
        this.owner = owner;
    }
}
