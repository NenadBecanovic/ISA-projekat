package com.application.bekend.DTO;

public class AdditionalServicesDTO {

    private Long boatId;
    private Long houseId;
    private Long adventureId;
    private Long id;
    private String name;
    private float price;
    private Boolean checked = false;

    public AdditionalServicesDTO(Long id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public AdditionalServicesDTO(Long id, String name, float price, boolean checked, Long houseId, Long boatId, Long adventureId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.checked = checked;
        this.houseId = houseId;
        this.boatId = boatId;
        this.adventureId = adventureId;
    }

    public AdditionalServicesDTO(Long id, String name, float price, boolean checked) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.checked = checked;
    }

    public AdditionalServicesDTO() {
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Long getBoatId() {
        return boatId;
    }

    public void setBoatId(Long boatId) {
        this.boatId = boatId;
    }

	public Long getAdventureId() {
		return adventureId;
	}

	public void setAdventureId(Long adventureId) {
		this.adventureId = adventureId;
	}
    
}
