package com.application.bekend.model;
import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boat_id")
    private Boat boat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fishing_adventure_id")
    private FishingAdventure fishingAdventure;

    public Image(Long id, String imageUrl, House house, Boat boat, FishingAdventure fishingAdventure) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.house = house;
        this.boat = boat;
        this.fishingAdventure = fishingAdventure;
    }

    public Image() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    public FishingAdventure getFishingAdventure() {
        return fishingAdventure;
    }

    public void setFishingAdventure(FishingAdventure fishingAdventure) {
        this.fishingAdventure = fishingAdventure;
    }
}
