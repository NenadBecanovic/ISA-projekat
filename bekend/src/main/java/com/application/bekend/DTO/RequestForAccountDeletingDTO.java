package com.application.bekend.DTO;

import com.application.bekend.model.MyUser;

import javax.persistence.*;

public class RequestForAccountDeletingDTO {


    private Long id;
    private MyUserDTO user;
    private String description;

    public RequestForAccountDeletingDTO(Long id, MyUserDTO user, String description) {
        this.id = id;
        this.user = user;        this.description = description;
    }

    public RequestForAccountDeletingDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MyUserDTO getUser() {
        return user;
    }

    public void setUser(MyUserDTO user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
