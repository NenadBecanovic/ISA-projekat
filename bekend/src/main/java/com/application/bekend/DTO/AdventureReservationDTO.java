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
    private UserInfoDTO guest;
	private boolean canBeCancelled = false;
	private boolean onGoing = false;
	private Long milisStartDate;
	private Long milisEndDate;
	private boolean hasFeedbackOwner = false;
	private boolean hasAppealOwner = false;
	private Double totalPrice;
	private String entityName;

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

	public AdventureReservationDTO(Long adventureId, Long id, String startDate, String endDate, int maxGuests, float price, boolean isAvailable) {
		this.adventureId = adventureId;
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.maxGuests = maxGuests;
		this.price = price;
		this.isAvailable = isAvailable;
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
	public boolean getAvailabilityPeriod() {
		return availabilityPeriod;
	}
	public void setAvailabilityPeriod(boolean availabilityPeriod) {
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
	public UserInfoDTO getGuest() {
		return guest;
	}

	public void setGuest(UserInfoDTO guest) {
		this.guest = guest;
	}

	public boolean getCanBeCancelled() {
		return canBeCancelled;
	}

	public void setCanBeCancelled(boolean canBeCancelled) {
		this.canBeCancelled = canBeCancelled;
	}

	public boolean getOnGoing() {
		return onGoing;
	}

	public void setOnGoing(boolean onGoing) {
		this.onGoing = onGoing;
	}

	public Long getMilisStartDate() {
		return milisStartDate;
	}

	public void setMilisStartDate(Long milisStartDate) {
		this.milisStartDate = milisStartDate;
	}

	public Long getMilisEndDate() {
		return milisEndDate;
	}

	public void setMilisEndDate(Long milisEndDate) {
		this.milisEndDate = milisEndDate;
	}

	public boolean isHasFeedbackOwner() {
		return hasFeedbackOwner;
	}

	public void setHasFeedbackOwner(boolean hasFeedbackOwner) {
		this.hasFeedbackOwner = hasFeedbackOwner;
	}

	public boolean isHasAppealOwner() {
		return hasAppealOwner;
	}

	public void setHasAppealOwner(boolean hasAppealOwner) {
		this.hasAppealOwner = hasAppealOwner;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
}
