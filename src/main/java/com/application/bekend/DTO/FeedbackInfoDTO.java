package com.application.bekend.DTO;

public class FeedbackInfoDTO {
	private Long id;
    private int grade;
    private String review;
    private Boolean isApproved;
    private String name;
    private String type;
    private Long ownerId;
    
	public FeedbackInfoDTO(Long id, int grade, String review, Boolean isApproved) {
		super();
		this.id = id;
		this.grade = grade;
		this.review = review;
		this.isApproved = isApproved;
	}
	
	public FeedbackInfoDTO() {
		
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
	public Boolean getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	
}
