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

    // u ManyToMany strana kod koje je joinTable je vodeca
    // vodeca strana ManyToMany veze, sto znaci ako nesto uklonimo sa strane AdditionalService uklonice se i sa druge strane ManyToMany veze
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "addiotional_services_house_reservation", joinColumns = @JoinColumn(name = "additional_services_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "house_reservation_id", referencedColumnName = "id"))
    private Set<HouseReservation> houseReservationsServices = new HashSet<HouseReservation>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "addiotional_services_boat_reservation", joinColumns = @JoinColumn(name = "additional_services_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "boat_reservation_id", referencedColumnName = "id"))
    private Set<BoatReservation> boatReservationsServices = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "addiotional_services_adventure_reservation", joinColumns = @JoinColumn(name = "additional_services_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "adventure_reservation_id", referencedColumnName = "id"))
    private Set<AdventureReservation> adventureReservationsServices = new HashSet<>();

    public AdditionalServices(Long id, String name, float price, Set<Boat> boats, Set<FishingAdventure> fishingAdventures, Set<House> houses) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.boats = boats;
        this.fishingAdventures = fishingAdventures;
        this.houses = houses;
    }

    public AdditionalServices(String name, float price, Set<Boat> boats) {
        this.name = name;
        this.price = price;
        this.boats = boats;
    }
/*
    public AdditionalServices(Long id, String name, float price, Set<HouseReservation> houseReservationsServices, Set<BoatReservation> boatReservationsServices, Set<AdventureReservation> adventureReservationsServices) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.houseReservationsServices = houseReservationsServices;
        this.boatReservationsServices = boatReservationsServices;
        this.adventureReservationsServices = adventureReservationsServices;
    }
*/
//    public AdditionalServices(Long id, String name, float price, Set<BoatReservation> boatReservationsServices) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.boatReservationsServices = boatReservationsServices;
//    }

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

    public Set<HouseReservation> getHouseReservationsServices() {
        return houseReservationsServices;
    }

    public void setHouseReservationsServices(Set<HouseReservation> houseReservationsServices) {
        this.houseReservationsServices = houseReservationsServices;
    }

    public Set<BoatReservation> getBoatReservationsServices() {
        return boatReservationsServices;
    }

    public void setBoatReservationsServices(Set<BoatReservation> boatReservationsServices) {
        this.boatReservationsServices = boatReservationsServices;
    }

    public void addHouseReservation(HouseReservation houseReservation){
        this.houseReservationsServices.add(houseReservation);
    }

    public void addBoatReservation(BoatReservation boatReservation){
        this.boatReservationsServices.add(boatReservation);
    }
    
    public void addAdventureReservation(AdventureReservation adventureReservation){
        this.adventureReservationsServices.add(adventureReservation);
    }

	public Set<AdventureReservation> getAdventureReservationsServices() {
		return adventureReservationsServices;
	}

	public void setAdventureReservationsServices(Set<AdventureReservation> adventureReservationsServices) {
		this.adventureReservationsServices = adventureReservationsServices;
	}
    
}
