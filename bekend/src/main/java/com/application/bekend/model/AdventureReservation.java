package com.application.bekend.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AdventureReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date startDate;
    private Date endDate;
    private int maxGuests;
    private float price;
    private boolean isAvailable;
    private Boolean hasFeedbackOwner = false;
    private Boolean hasAppealOwner = false;
    private boolean availabilityPeriod = false;
    private boolean isAction = false;
    private boolean hasReport = false;
    private Boolean isCancelled = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id")
    private MyUser guest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fishing_adventure_id")
    private FishingAdventure fishingAdventure;
    
    @ManyToMany(mappedBy = "adventureReservationsServices")
    private Set<AdditionalServices> additionalServices = new HashSet<>();
    
    @OneToOne(mappedBy = "adventureReservation")
    private Report report;
    
    public AdventureReservation(Long id, Date startDate, Date endDate, int maxGuests, float price, boolean isAvailable, FishingAdventure fishingAdventure) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGuests = maxGuests;
        this.price = price;
        this.isAvailable = isAvailable;
        this.fishingAdventure = fishingAdventure;
    }

    public AdventureReservation() {
    }

    public AdventureReservation(Date startDate, Date endDate, int maxGuests, float price, boolean isAvailable, FishingAdventure fishingAdventure) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGuests = maxGuests;
        this.price = price;
        this.isAvailable = isAvailable;
        this.fishingAdventure = fishingAdventure;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public Set<AdditionalServices> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(Set<AdditionalServices> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public void addAdditionalService(AdditionalServices additionalServices){
        this.additionalServices.add(additionalServices);
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public FishingAdventure getFishingAdventure() {
        return fishingAdventure;
    }

    public void setFishingAdventure(FishingAdventure fishingAdventure) {
        this.fishingAdventure = fishingAdventure;
    }

    public Boolean getHasFeedbackOwner() {
        return hasFeedbackOwner;
    }

    public void setHasFeedbackOwner(Boolean hasFeedbackOwner) {
        this.hasFeedbackOwner = hasFeedbackOwner;
    }

    public Boolean getHasAppealOwner() {
        return hasAppealOwner;
    }

    public void setHasAppealOwner(Boolean hasAppealOwner) {
        this.hasAppealOwner = hasAppealOwner;
    }
    
	public MyUser getGuest() {
		return guest;
	}

	public void setGuest(MyUser guest) {
		this.guest = guest;
	}

	public boolean isAvailabilityPeriod() {
		return availabilityPeriod;
	}

	public void setAvailabilityPeriod(boolean availabilityPeriod) {
		this.availabilityPeriod = availabilityPeriod;
	}

	public boolean isAction() {
		return isAction;
	}

	public void setAction(boolean isAction) {
		this.isAction = isAction;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public boolean isHasReport() {
		return hasReport;
	}

	public void setHasReport(boolean hasReport) {
		this.hasReport = hasReport;
	}

    public Boolean getCancelled() {
        return isCancelled;
    }

    public void setCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }
}
