package com.application.bekend.DTO;

public class UserCategoryDTO {

	private Long id;
	private int points;
	private String name;
	private int discountPercentage;
	
	
	public UserCategoryDTO(Long id, String name, int discountPercentage, int points) {
		super();
		this.id = id;
		this.name = name;
		this.discountPercentage = discountPercentage;
		this.points = points;
	}
	
	public UserCategoryDTO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
