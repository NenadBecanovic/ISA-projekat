package com.application.bekend.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AdditionalServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float price;

    @ManyToMany
    @JoinTable(name = "addiotional_services_boat", joinColumns = @JoinColumn(name = "additional_services_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "boat_id", referencedColumnName = "id"))
    private Set<Boat> boats = new HashSet<Boat>();

    @ManyToMany
    @JoinTable(name = "addiotional_services_house", joinColumns = @JoinColumn(name = "additional_services_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "house_id", referencedColumnName = "id"))
    private Set<House> houses = new HashSet<House>();

    @ManyToMany
    @JoinTable(name = "addiotional_services_adventure", joinColumns = @JoinColumn(name = "additional_services_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "fishing_adventure_id", referencedColumnName = "id"))
    private Set<FishingAdventure> fishingAdventures = new HashSet<FishingAdventure>();

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade =
//                    {
//                            CascadeType.DETACH,
//                            CascadeType.MERGE,
//                            CascadeType.REFRESH,
//                            CascadeType.PERSIST
//                    },
//            targetEntity = HouseReservation.class)
//    @JoinTable(name = "additional_services_house_reservation",
//            joinColumns = @JoinColumn(name = "house_reservation_id",
//                    nullable = false,
//                    updatable = false),
//            inverseJoinColumns = @JoinColumn(name = "additional_services_id",
//                    nullable = false,
//                    updatable = false),
//            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
//            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
//    private Set<HouseReservation> houseReservationsServices = new HashSet<>();

    // u ManyToMany strana kod koje je joinTable je vodeca
    // vodeca strana ManyToMany veze, sto znaci ako nesto uklonimo sa strane AdditionalService uklonice se i sa druge strane ManyToMany veze
    @ManyToMany
    @JoinTable(name = "addiotional_services_house_reservation", joinColumns = @JoinColumn(name = "additional_services_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "house_reservation_id", referencedColumnName = "id"))
    private Set<HouseReservation> houseReservationsServices = new HashSet<HouseReservation>();

    public AdditionalServices(Long id, String name, float price, Set<Boat> boats, Set<FishingAdventure> fishingAdventures, Set<House> houses) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.boats = boats;
        this.fishingAdventures = fishingAdventures;
        this.houses = houses;
    }

    public AdditionalServices(Long id, String name, float price, Set<HouseReservation> houseReservationsServices) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.houseReservationsServices = houseReservationsServices;
    }

    public AdditionalServices() {
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

    public Set<Boat> getBoats() {
        return boats;
    }

    public void setBoats(Set<Boat> boats) {
        this.boats = boats;
    }

    public Set<House> getHouses() {
        return houses;
    }

    public void setHouses(Set<House> houses) {
        this.houses = houses;
    }

    public Set<FishingAdventure> getFishingAdventures() {
        return fishingAdventures;
    }

    public void setFishingAdventures(Set<FishingAdventure> fishingAdventures) { this.fishingAdventures = fishingAdventures; }

    public void addHouseReservation(HouseReservation houseReservation){
        this.houseReservationsServices.add(houseReservation);
    }

    public Set<HouseReservation> getHouseReservationsServices() {
        return houseReservationsServices;
    }

    public void setHouseReservationsServices(Set<HouseReservation> houseReservationsServices) {
        this.houseReservationsServices = houseReservationsServices;
    }
}
