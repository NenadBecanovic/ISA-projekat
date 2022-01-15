package com.application.bekend.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    private Timestamp expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private MyUser user;

    public VerificationToken() {
    }

    public VerificationToken(String token, MyUser user) {
        this.token = token;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public MyUser getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }
}
