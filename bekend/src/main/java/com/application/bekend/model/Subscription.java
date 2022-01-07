package com.application.bekend.model;

import javax.persistence.*;

@Entity
public class Subscription {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subscribed_user_id")
    private MyUser subscribedUser;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private MyUser owner;

    public Subscription(Long id, MyUser subscribedUser, MyUser owner) {
        this.id = id;
        this.subscribedUser = subscribedUser;
        this.owner = owner;
    }

    public Subscription() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MyUser getSubscribedUser() {
        return subscribedUser;
    }

    public void setSubscribedUser(MyUser subscribedUser) {
        this.subscribedUser = subscribedUser;
    }

    public MyUser getOwner() {
        return owner;
    }

    public void setOwner(MyUser owner) {
        this.owner = owner;
    }
}
