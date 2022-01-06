package com.application.bekend.model;

import javax.persistence.*;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int grade;
    private String review;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "myUser_id")
    private MyUser myUser;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boat_id")
    private Boat boat;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "house_id")
    private House house;

    public Feedback(Long id, int grade, String review) {
        this.id = id;
        this.grade = grade;
        this.review = review;
    }

    public Feedback() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
