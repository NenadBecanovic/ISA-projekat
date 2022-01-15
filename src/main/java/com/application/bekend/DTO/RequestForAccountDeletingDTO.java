package com.application.bekend.DTO;

import com.application.bekend.model.MyUser;

import javax.persistence.*;

public class RequestForAccountDeletingDTO {


    private Long id;
    private String description;
    private UserInfoDTO userInfo;
    private boolean isAnswered;

    public RequestForAccountDeletingDTO(Long id, String description, UserInfoDTO userInfo) {
        this.id = id;
        this.description = description;
        this.userInfo = userInfo;
    }

    public RequestForAccountDeletingDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public UserInfoDTO getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoDTO userInfo) {
		this.userInfo = userInfo;
	}

	public boolean getIsAnswered() {
		return isAnswered;
	}

	public void setIsAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
	}
}
