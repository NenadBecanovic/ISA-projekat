package com.application.bekend.DTO;

import com.application.bekend.model.Address;

public class FishingAdventureForInstructorProfileDTO {
	
	private Long id;
    private String name;
    private AddressDTO address;
    private String promoDescription;
    private int capacity;
    private float pricePerHour;
    
	public FishingAdventureForInstructorProfileDTO(Long id, String name, AddressDTO address, String promoDescription,
			int capacity, float pricePerHour) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.promoDescription = promoDescription;
		this.capacity = capacity;
		this.pricePerHour = pricePerHour;
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

	public float getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(float pricePerHour) {
		this.pricePerHour = pricePerHour;
	}
}
