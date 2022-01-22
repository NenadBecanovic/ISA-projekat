package com.application.bekend.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Boat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Integer version;

    private String name;
    private String type;
    private float length;
    private int engineNumber;
    private float enginePower;
    private float maxSpeed;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "navigationEquipment_id")
    private NavigationEquipment navigationEquipment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToMany(mappedBy = "boat", fetch = FetchType.EAGER)
    private Set<Feedback> feedbacks= new HashSet<>();
    private String promoDescription;
    private int capacity;
    @Column(name = "grade", nullable = true)
    private double grade;
    @Column(name = "numberOfReviews", nullable = true)
    private int numberOfReviews;
    @OneToMany(mappedBy = "boat", fetch = FetchType.EAGER)
    private Set<Image> images;

    @OneToMany(mappedBy = "boat", fetch = FetchType.EAGER)
    private Set<BoatReservation> courses = new HashSet<BoatReservation>();
    private String behaviourRules;
    private String fishingEquipment;
    private float pricePerDay;

    @ManyToMany(mappedBy = "boats", fetch = FetchType.EAGER)
    private Set<AdditionalServices> services = new HashSet<AdditionalServices>();
    private boolean isCancalletionFree;
    private int cancalletionFee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private MyUser owner;

    @OneToMany(mappedBy = "boat", fetch = FetchType.EAGER)
    private Set<Appeal> appeals = new HashSet<>();

    public Boat(Long id, String name, String type, float length, int engineNumber, float enginePower, float maxSpeed,
                NavigationEquipment navigationEquipment, Address address, String promoDescription, int capacity,
                double grade, Set<BoatReservation> courses, String behaviourRules, String fishingEquipment, float pricePerDay,
                Set<AdditionalServices> services, boolean isCancalletionFree, int cancalletionFee, Set<Image> images) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.length = length;
        this.engineNumber = engineNumber;
        this.enginePower = enginePower;
        this.maxSpeed = maxSpeed;
        this.navigationEquipment = navigationEquipment;
        this.address = address;
        this.promoDescription = promoDescription;
        this.capacity = capacity;
        this.grade = grade;
        this.courses = courses;
        this.behaviourRules = behaviourRules;
        this.fishingEquipment = fishingEquipment;
        this.pricePerDay = pricePerDay;
        this.services = services;
        this.isCancalletionFree = isCancalletionFree;
        this.cancalletionFee = cancalletionFee;
        this.images = images;
    }

    public Boat(Long id, String name, String type, float length, int engineNumber, float enginePower, float maxSpeed, String promoDescription, int capacity, double grade, int numberOfReviews, String behaviourRules, String fishingEquipment, float pricePerDay, boolean isCancalletionFree, int cancalletionFee) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.length = length;
        this.engineNumber = engineNumber;
        this.enginePower = enginePower;
        this.maxSpeed = maxSpeed;
        this.promoDescription = promoDescription;
        this.capacity = capacity;
        this.grade = grade;
        this.numberOfReviews = numberOfReviews;
        this.behaviourRules = behaviourRules;
        this.fishingEquipment = fishingEquipment;
        this.pricePerDay = pricePerDay;
        this.isCancalletionFree = isCancalletionFree;
        this.cancalletionFee = cancalletionFee;
    }

    public Boat() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public int getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(int engineNumber) {
        this.engineNumber = engineNumber;
    }

    public float getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(float enginePower) {
        this.enginePower = enginePower;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public NavigationEquipment getNavigationEquipment() {
        return navigationEquipment;
    }

    public void setNavigationEquipment(NavigationEquipment navigationEquipment) {
        this.navigationEquipment = navigationEquipment;
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

    public Set<BoatReservation> getCourses() {
        return courses;
    }

    public void setCourses(Set<BoatReservation> courses) {
        this.courses = courses;
    }

    public String getBehaviourRules() {
        return behaviourRules;
    }

    public void setBehaviourRules(String behaviourRules) {
        this.behaviourRules = behaviourRules;
    }

    public String getFishingEquipment() {
        return fishingEquipment;
    }

    public void setFishingEquipment(String fishingEquipment) {
        this.fishingEquipment = fishingEquipment;
    }

    public float getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(float pricePerDay) {
        this.pricePerDay = pricePerDay;
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

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public double getGrade() {return grade;}

    public void setGrade(double grade) {this.grade = grade;}

    public MyUser getOwner() {
        return owner;
    }

    public void setOwner(MyUser owner) {
        this.owner = owner;
    }

    public void addBoatReservation(BoatReservation boatReservation)
    {
        this.courses.add(boatReservation);
    }

    public Set<Appeal> getAppeals() {
        return appeals;
    }

    public void setAppeals(Set<Appeal> appeals) {
        this.appeals = appeals;
    }

	public int getNumberOfReviews() {
		return numberOfReviews;
	}

	public void setNumberOfReviews(int numberOfReviews) {
		this.numberOfReviews = numberOfReviews;
	}

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void removeBoatReservation(BoatReservation boatReservation)
    {
        this.courses.remove(boatReservation);
    }

}
