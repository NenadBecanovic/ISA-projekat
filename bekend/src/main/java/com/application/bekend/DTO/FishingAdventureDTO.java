package com.application.bekend.DTO;

import java.util.Set;

public class FishingAdventureDTO {

    private Long id;
    private String name;
    private AddressDTO address;
    private String promoDescription;
    private int capacity;
    private String fishingEquipment;
    private String behaviourRules;
    private float pricePerHour;
    private boolean cancellationFree;
    private int cancellationFee;
    private Set<ImageDTO> images;
    private Set<AdditionalServicesDTO> additionalServices;
    private Long instructorId;
    private double grade;
    private int numberOfReviews;
    
	public FishingAdventureDTO(Long id, String name, AddressDTO address, String promoDescription, int capacity,
			String fishingEquipment, String behaviourRules, float pricePerHour, boolean isCancalletionFree,
			int cancalletionFee, double grade, int numberOfReviews) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.promoDescription = promoDescription;
		this.capacity = capacity;
		this.fishingEquipment = fishingEquipment;
		this.behaviourRules = behaviourRules;
		this.pricePerHour = pricePerHour;
		this.cancellationFree = isCancalletionFree;
		this.cancellationFee = cancalletionFee;
		this.grade = grade;
		this.numberOfReviews = numberOfReviews;
	}
	
	public FishingAdventureDTO() {
		
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
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	public String getPromoDescription() {
		return promoDescription;
	}
	public void setPromoDescription(String promoDescription) {
		this.promoDescription = promoDescription;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public String getFishingEquipment() {
		return fishingEquipment;
	}
	public void setFishingEquipment(String fishingEquipment) {
		this.fishingEquipment = fishingEquipment;
	}
	public String getBehaviourRules() {
		return behaviourRules;
	}
	public void setBehaviourRules(String behaviourRules) {
		this.behaviourRules = behaviourRules;
	}
	public float getPricePerHour() {
		return pricePerHour;
	}
	public void setPricePerHour(float pricePerHour) {
		this.pricePerHour = pricePerHour;
	}
	public boolean isCancellationFree() {
		return cancellationFree;
	}
	public void setCancellationFree(boolean isCancalletionFree) {
		this.cancellationFree = isCancalletionFree;
	}
	public int getCancellationFee() {
		return cancellationFee;
	}
	public void setCancellationFee(int cancellationFee) {
		this.cancellationFee = cancellationFee;
	}
    
    public Set<ImageDTO> getImages() {
        return images;
    }

    public void setImages(Set<ImageDTO> images) {
        this.images = images;
    }
	public Set<AdditionalServicesDTO> getAdditionalServices() {
		return additionalServices;
	}
	public void setAdditionalServices(Set<AdditionalServicesDTO> additionalServices) {
		this.additionalServices = additionalServices;
	}

	public Long getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(Long instructorId) {
		this.instructorId = instructorId;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public int getNumberOfReviews() {
		return numberOfReviews;
	}

	public void setNumberOfReviews(int numberOfReviews) {
		this.numberOfReviews = numberOfReviews;
	}
}
