package com.application.bekend.model;

import javax.persistence.*;

@Entity
public class Appeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String review;
    private boolean isAnswered;
    
    @Version
	@Column(nullable = false)
	private Integer version;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private MyUser ownerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private MyUser senderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boat_id")
    private Boat boat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fishing_adventure_id")
    private FishingAdventure fishingAdventure;

    public Appeal(Long id, String review, MyUser ownerId, MyUser senderId, Boat boat, House house, FishingAdventure fishingAdventure) {
        this.id = id;
        this.review = review;
        this.ownerId = ownerId;
        this.senderId = senderId;
        this.boat = boat;
        this.house = house;
        this.fishingAdventure = fishingAdventure;
    }

    public Appeal() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public MyUser getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(MyUser ownerId) {
        this.ownerId = ownerId;
    }

    public MyUser getSenderId() {
        return senderId;
    }

    public void setSenderId(MyUser senderId) {
        this.senderId = senderId;
    }

    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public FishingAdventure getFishingAdventure() {
        return fishingAdventure;
    }

    public void setFishingAdventure(FishingAdventure fishingAdventure) {
        this.fishingAdventure = fishingAdventure;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
