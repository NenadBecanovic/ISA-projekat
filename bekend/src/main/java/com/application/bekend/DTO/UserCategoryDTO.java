package com.application.bekend.DTO;

public class UserCategoryDTO {

	private int id;
	private String name;
	private int discountPercentage;
	private int points;
	
	public UserCategoryDTO(int id, String name, int discountPercentage, int points) {
		super();
		this.id = id;
		this.name = name;
		this.discountPercentage = discountPercentage;
		this.points = points;
	}
	
	public UserCategoryDTO() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
