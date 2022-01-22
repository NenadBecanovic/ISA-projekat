package com.application.bekend.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Integer version;

    private String name;
    @Column(name = "grade", nullable = true)
    private double grade;
    @Column(name = "numberOfReviews", nullable = true)
    private int numberOfReviews;
    @OneToMany(mappedBy = "house", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;
    private String promoDescription;

    @OneToMany(mappedBy = "house", fetch = FetchType.EAGER)
    private Set<Feedback> feedbacks= new HashSet<>();

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY)
    private Set<Appeal> appeals= new HashSet<>();

    @OneToMany(mappedBy = "house", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Image> images;

    @OneToMany(mappedBy = "house", fetch = FetchType.EAGER)
    private Set<HouseReservation> courses = new HashSet<>();
    private String behaviourRules;
    private float pricePerDay;

    @ManyToMany(mappedBy = "houses", fetch = FetchType.EAGER)
    private Set<AdditionalServices> services = new HashSet<>();
    private boolean isCancalletionFree;
    private int cancalletionFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private MyUser owner;

    public House(Long id, String name, double grade, Set<Room> rooms, Address address, String promoDescription, Set<HouseReservation> courses,
                 String behaviourRules, float pricePerDay, Set<AdditionalServices> services, boolean isCancalletionFree,
                 int cancalletionFee, Set<Image> images) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.rooms = rooms;
        this.address = address;
        this.promoDescription = promoDescription;
        this.courses = courses;
        this.behaviourRules = behaviourRules;
        this.pricePerDay = pricePerDay;
        this.services = services;
        this.isCancalletionFree = isCancalletionFree;
        this.cancalletionFee = cancalletionFee;
        this.images = images;
    }

    public House(Long id, String name, double grade, int numberOfReviews, String promoDescription, String behaviourRules, float pricePerDay, boolean isCancalletionFree, int cancalletionFee) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.numberOfReviews = numberOfReviews;
        this.promoDescription = promoDescription;
        this.behaviourRules = behaviourRules;
        this.pricePerDay = pricePerDay;
        this.isCancalletionFree = isCancalletionFree;
        this.cancalletionFee = cancalletionFee;
    }

    public House() {
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

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
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

    public Set<HouseReservation> getCourses() {
        return courses;
    }

    public void setCourses(Set<HouseReservation> courses) {
        this.courses = courses;
    }

    public String getBehaviourRules() {
        return behaviourRules;
    }

    public void setBehaviourRules(String behaviourRules) {
        this.behaviourRules = behaviourRules;
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

    public MyUser getOwner() {
        return owner;
    }

    public void setOwner(MyUser owner) {
        this.owner = owner;
    }

    public void addHouseReservation(HouseReservation houseReservation)
    {
        this.courses.add(houseReservation);
    }

    public double getGrade() {return grade;}

    public void setGrade(double grade) {this.grade = grade;}

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
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
}
