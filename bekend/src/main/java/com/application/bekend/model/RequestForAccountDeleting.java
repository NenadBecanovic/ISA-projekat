package com.application.bekend.model;

import javax.persistence.*;

@Entity
public class RequestForAccountDeleting {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private MyUser user;
    private String description;
    private boolean isReviewed = false;
    
    public RequestForAccountDeleting(Long id, MyUser user, String description) {
        this.id = id;
        this.user = user;
        this.description = description;
    }

    public RequestForAccountDeleting() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public boolean isReviewed() {
		return isReviewed;
	}

	public void setReviewed(boolean isReviewed) {
		this.isReviewed = isReviewed;
	}
}
