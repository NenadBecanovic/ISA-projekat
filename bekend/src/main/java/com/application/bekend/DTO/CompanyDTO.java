package com.application.bekend.DTO;

import java.util.HashSet;
import java.util.Set;

public class CompanyDTO {
	
	private int percentagePerReservation;
	private int pointsPerReservationClient;
	private int pointsPerReservationOwner;
	private Set<UserCategoryDTO> userCategories = new HashSet<UserCategoryDTO>();
	
	public CompanyDTO(int percentagePerReservation, int pointsPerReservationClient, int pointsPerReservationOwner) {
		super();
		this.percentagePerReservation = percentagePerReservation;
		this.pointsPerReservationClient = pointsPerReservationClient;
		this.pointsPerReservationOwner = pointsPerReservationOwner;
	}
	
	public CompanyDTO() {
		
	}

	public int getPercentagePerReservation() {
		return percentagePerReservation;
	}

	public void setPercentagePerReservation(int percentagePerReservation) {
		this.percentagePerReservation = percentagePerReservation;
	}

	public int getPointsPerReservationClient() {
		return pointsPerReservationClient;
	}

	public void setPointsPerReservationClient(int pointsPerReservationClient) {
		this.pointsPerReservationClient = pointsPerReservationClient;
	}

	public int getPointsPerReservationOwner() {
		return pointsPerReservationOwner;
	}

	public void setPointsPerReservationOwner(int pointsPerReservationOwner) {
		this.pointsPerReservationOwner = pointsPerReservationOwner;
	}

	public Set<UserCategoryDTO> getUserCategories() {
		return userCategories;
	}

	public void setUserCategories(Set<UserCategoryDTO> userCategories) {
		this.userCategories = userCategories;
	}
	
	public void addUserCategory(UserCategoryDTO userCategory) {
		this.userCategories.add(userCategory);
	}
}
