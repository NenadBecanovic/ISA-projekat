package com.application.bekend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Boat> boats = new HashSet<Boat>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<House> houses = new HashSet<House>();

    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FishingAdventure> fishingAdventures = new HashSet<FishingAdventure>();

    @ManyToMany
    @JoinTable(name = "house_reservations", joinColumns = @JoinColumn(name = "guest_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "house_reservation_id", referencedColumnName = "id"))
    private Set<HouseReservation> houseReservations = new HashSet<HouseReservation>();

    @ManyToMany
    @JoinTable(name = "boat_reservations", joinColumns = @JoinColumn(name = "guest_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "boat_reservation_id", referencedColumnName = "id"))
    private Set<BoatReservation> boatReservations = new HashSet<BoatReservation>();

    @ManyToMany
    @JoinTable(name = "fishing_reservations", joinColumns = @JoinColumn(name = "guest_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "fishing_reservation_id", referencedColumnName = "id"))
    private Set<AdventureReservation> adventureReservations = new HashSet<AdventureReservation>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities = new ArrayList<>();


    @OneToOne(mappedBy = "user")
    private VerificationRequest verificationRequest;


    private Boolean isActivated;

    public MyUser(Long id, String firstName, String lastName, String email, String password, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
    }
    
    public MyUser() {
    	
    }


    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActivated;
    }


    public void addAuthority(Authority authority){
        this.authorities.add(authority);
   }


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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


	public void setFishingAdventures(Set<FishingAdventure> fishingAdventures) {
		this.fishingAdventures = fishingAdventures;
	}


	public Set<HouseReservation> getHouseReservations() {
		return houseReservations;
	}


	public void setHouseReservations(Set<HouseReservation> houseReservations) {
		this.houseReservations = houseReservations;
	}


	public Set<BoatReservation> getBoatReservations() {
		return boatReservations;
	}


	public void setBoatReservations(Set<BoatReservation> boatReservations) {
		this.boatReservations = boatReservations;
	}


	public Set<AdventureReservation> getAdventureReservations() {
		return adventureReservations;
	}


	public void setAdventureReservations(Set<AdventureReservation> adventureReservations) {
		this.adventureReservations = adventureReservations;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}


	public List<Authority> getAuthorities() {
		return authorities;
	}


	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}


	public VerificationRequest getVerificationRequest() {
		return verificationRequest;
	}


	public void setVerificationRequest(VerificationRequest verificationRequest) {
		this.verificationRequest = verificationRequest;
	}


	public Boolean getIsActivated() {
		return isActivated;
	}


	public void setIsActivated(Boolean isActivated) {
		this.isActivated = isActivated;
	}
  
}
