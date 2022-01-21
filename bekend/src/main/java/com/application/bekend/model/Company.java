package com.application.bekend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Company {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "percentage__per_reservation", nullable = true)
    private int percentagePerReservation;
    @Column(name = "points_reservation_client", nullable = true)
    private int pointsPerReservationClient;
    @Column(name = "points_reservation_owner", nullable = true)
    private int pointsPerReservationOwner;
    
	public Company(Long id, int percentagePerReservation, int pointsPerReservationClient,
			int pointsPerReservationOwner) {
		super();
		this.id = id;
		this.percentagePerReservation = percentagePerReservation;
		this.pointsPerReservationClient = pointsPerReservationClient;
		this.pointsPerReservationOwner = pointsPerReservationOwner;
	}
	
	public Company() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}
