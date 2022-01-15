package com.application.bekend.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class VerificationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private MyUser user;

    private boolean isApproved;
    private String reasonForRegistration;

    public VerificationRequest() {
    }

    public VerificationRequest(MyUser user, boolean isApproved, String reasonForRegistration) {
        this.user = user;
        this.isApproved = isApproved;
        this.reasonForRegistration = reasonForRegistration;
    }
}
