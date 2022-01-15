package com.application.bekend.DTO;

import java.util.Set;

public class NewFishingAdventureDTO {
	private String name;
	private AddressDTO address;
	private String promoDescription;
	private int capacity;
	private String fishingEquipment;
	private String behaviourRules;
	private float pricePerHour;
	private boolean isCancellationFree;
	private int cancellationFee;
	private Set<AdditionalServicesDTO> additionalServices;
	private String image;
	private Long instructorId;
	
	public NewFishingAdventureDTO() {
		
	}

	public NewFishingAdventureDTO(String name, AddressDTO address, String promoDescription, int capacity,
			String fishingEquipment, String behaviourRules, float pricePerHour, boolean isCancellationFree,
			int cancellationFee, Set<AdditionalServicesDTO> additionalServices, String image) {
		super();
		this.name = name;
		this.address = address;
		this.promoDescription = promoDescription;
		this.capacity = capacity;
		this.fishingEquipment = fishingEquipment;
		this.behaviourRules = behaviourRules;
		this.pricePerHour = pricePerHour;
		this.isCancellationFree = isCancellationFree;
		this.cancellationFee = cancellationFee;
		this.additionalServices = additionalServices;
		this.image = image;
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
		return isCancellationFree;
	}

	public void setCancellationFree(boolean isCancellationFree) {
		this.isCancellationFree = isCancellationFree;
	}

	public int getCancellationFee() {
		return cancellationFee;
	}

	public void setCancellationFee(int cancellationFee) {
		this.cancellationFee = cancellationFee;
	}

	public Set<AdditionalServicesDTO> getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(Set<AdditionalServicesDTO> additionalServices) {
		this.additionalServices = additionalServices;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(Long instructorId) {
		this.instructorId = instructorId;
	}
}
