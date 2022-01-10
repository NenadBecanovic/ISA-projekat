package com.application.bekend.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class FishingAdventure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;
    private String promoDescription;
    private int capacity;
    private String fishingEquipment;
    @Column(name = "grade", nullable = true)
    private double grade;
    
    @OneToMany(mappedBy = "fishingAdventure", fetch = FetchType.EAGER)
    private Set<Feedback> feedbacks= new HashSet<>();

    @OneToMany(mappedBy = "fishingAdventure", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Image> images;

    @OneToMany(mappedBy = "fishingAdventure", fetch = FetchType.EAGER)
    private Set<AdventureReservation> adventureReservations = new HashSet<AdventureReservation>();
    private String behaviourRules;
    private float pricePerHour;

    @ManyToMany(mappedBy = "fishingAdventures")
    private Set<AdditionalServices> services = new HashSet<AdditionalServices>();
    private boolean isCancalletionFree;
    private int cancalletionFee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_id")
    private MyUser instructor;

    @OneToMany(mappedBy = "fishingAdventure", fetch = FetchType.EAGER)
    private Set<Appeal> appeals = new HashSet<>();

    public FishingAdventure(Long id, String name, Address address, String promoDescription, int capacity, String fishingEquipment,
                            Set<AdventureReservation> adventureReservations, String behaviourRules, float pricePerHour,
                            Set<AdditionalServices> services, boolean isCancalletionFree, int cancalletionFee, Set<Image> images) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.promoDescription = promoDescription;
        this.capacity = capacity;
        this.fishingEquipment = fishingEquipment;
        this.adventureReservations = adventureReservations;
        this.behaviourRules = behaviourRules;
        this.pricePerHour = pricePerHour;
        this.services = services;
        this.isCancalletionFree = isCancalletionFree;
        this.cancalletionFee = cancalletionFee;
        this.images = images;
    }

    public FishingAdventure() {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
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

    public Set<AdventureReservation> getAdventureReservations() {
        return adventureReservations;
    }

    public void setAdventureReservations(Set<AdventureReservation> adventureReservations) {
        this.adventureReservations = adventureReservations;
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

    public Set<AdditionalServices> getServices() {
        return services;
    }

    public void setServices(Set<AdditionalServices> services) {
        this.services = services;
    }

    public boolean isCancalletionFree() {
        return isCancalletionFree;
    }

    public void setCancalletionFree(boolean cancalletionFree) {
        isCancalletionFree = cancalletionFree;
    }

    public int getCancalletionFee() {
        return cancalletionFee;
    }

    public void setCancalletionFee(int cancalletionFee) {
        this.cancalletionFee = cancalletionFee;
    }

    public Set<Image> getImages() { return images; }

    public void setImages(Set<Image> images) { this.images = images; }
    
    public void addImage(Image image) {
    	this.images.add(image);
    }

    public MyUser getInstructor() {
        return instructor;
    }

    public void setInstructor(MyUser instructor) {
        this.instructor = instructor;
    }

    public Set<Appeal> getAppeals() {
        return appeals;
    }

    public void setAppeals(Set<Appeal> appeals) {
        this.appeals = appeals;
    }
    
	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public Set<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(Set<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}
	
	public void addAdventureReservation(AdventureReservation adventureReservation)
    {
        this.adventureReservations.add(adventureReservation);
    }
}
