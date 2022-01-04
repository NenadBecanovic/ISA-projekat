package com.application.bekend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String rewiew;
    private int grade;
    private boolean isApproved;


    public Feedback(Long id, String rewiew, int grade, boolean isApproved) {
        this.id = id;
        this.rewiew = rewiew;
        this.grade = grade;
        this.isApproved = isApproved;
    }

    public Feedback() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRewiew() {
        return rewiew;
    }

    public void setRewiew(String rewiew) {
        this.rewiew = rewiew;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
