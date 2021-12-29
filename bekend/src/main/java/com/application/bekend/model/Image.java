package com.application.bekend.model;
import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "house_images")
    private House house;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boat_images")
    private Boat boat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fishing_adventure_images")
    private FishingAdventure fishingAdventure;

    public Image(Long id, String imageUrl, House house, Boat boat, FishingAdventure fishingAdventure) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.house = house;
        this.boat = boat;
        this.fishingAdventure = fishingAdventure;
    }

    public Image() {}
}
