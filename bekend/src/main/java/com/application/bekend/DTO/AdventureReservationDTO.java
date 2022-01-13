package com.application.bekend.DTO;

import java.util.Set;

public class AdventureReservationDTO {

	private Long adventureId;
    private Long id;
    private String startDate;
    private String endDate;
    private int maxGuests;
    private float price;
    private boolean isAvailable;
    private Set<AdditionalServicesDTO> additionalServices;
    private boolean availabilityPeriod;
    private boolean isAction;
    private Long guestId;
    private boolean hasReport;
	private boolean cancelled = false;

	public AdventureReservationDTO(Long id, String startDate, String endDate, int maxGuests, float price, boolean isAvailable) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.maxGuests = maxGuests;
		this.price = price;
		this.isAvailable = isAvailable;
	}
	
	public AdventureReservationDTO() {
		
	}
	public Long getAdventureId() {
		return adventureId;
	}
	public void setAdventureId(Long adventureId) {
		this.adventureId = adventureId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getMaxGuests() {
		return maxGuests;
	}
	public void setMaxGuests(int maxGuests) {
		this.maxGuests = maxGuests;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public boolean getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public Set<AdditionalServicesDTO> getAdditionalServices() {
		return additionalServices;
	}
	public void setAdditionalServices(Set<AdditionalServicesDTO> additionalServices) {
		this.additionalServices = additionalServices;
	}
	public boolean getIsAvailabilityPeriod() {
		return availabilityPeriod;
	}
	public void setIAvailabilityPeriod(boolean availabilityPeriod) {
		this.availabilityPeriod = availabilityPeriod;
	}
	public boolean getIsAction() {
		return isAction;
	}
	public void setIsAction(boolean isAction) {
		this.isAction = isAction;
	}
	public Long getGuestId() {
		return guestId;
	}
	public void setGuestId(Long guestId) {
		this.guestId = guestId;
	}
	public boolean getHasReport() {
		return hasReport;
	}
	public void setHasReport(boolean hasReport) {
		this.hasReport = hasReport;
	}

	public boolean getCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
