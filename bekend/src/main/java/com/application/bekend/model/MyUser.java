package com.application.bekend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.*;

@Entity
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
    @Column(name = "grade", nullable = true)
    private double grade;
    @Column(name = "penalties")
    private int penalties;
    @Column(name = "isDeleted")
    private boolean isDeleted;
    @Column(name = "reasonForRegistration", nullable = true)
    private String reasonForRegistration;
    @Column(name = "personalDescription", nullable = true)
    private String personalDescription;
    
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Boat> boats = new HashSet<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<House> houses = new HashSet<>();

    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FishingAdventure> fishingAdventures = new HashSet<>();

    @OneToMany(mappedBy = "guest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<HouseReservation> houseReservations = new HashSet<>();

    @OneToMany(mappedBy = "guest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<BoatReservation> boatReservations = new HashSet<>();
    
    @OneToMany(mappedBy = "guest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AdventureReservation> adventureReservations = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "myUser", fetch = FetchType.EAGER)
    private Set<Feedback> feedbacks= new HashSet<>();

    @OneToMany(mappedBy = "senderId", fetch = FetchType.EAGER)
    private Set<Appeal> appealsSent= new HashSet<>();

    @OneToMany(mappedBy = "ownerId", fetch = FetchType.EAGER)
    private Set<Appeal> appelsFor = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private VerificationRequest verificationRequest;

    private Boolean isActivated;

    @OneToMany(mappedBy = "subscribedUser", fetch = FetchType.LAZY)
    private Set<Subscription> subscriptions_guests = new HashSet<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Subscription> subscriptions_owners = new HashSet<>();

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

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
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

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public Boolean getIsActivated() {
		return isActivated;
	}


	public void setIsActivated(Boolean isActivated) {
		this.isActivated = isActivated;
	}
  
    public int getPenalties() {
        return penalties;
    }

    public void setPenalties(int penalties) {
        this.penalties = penalties;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
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

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
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

    public Boolean getActivated() {
        return isActivated;
    }

    public void setActivated(Boolean activated) {
        isActivated = activated;
    }

    public Set<Subscription> getSubscriptions_guests() {
        return subscriptions_guests;
    }

    public void setSubscriptions_guests(Set<Subscription> subscriptions_guests) {
        this.subscriptions_guests = subscriptions_guests;
    }

    public Set<Subscription> getSubscriptions_owners() {
        return subscriptions_owners;
    }

    public void setSubscriptions_owners(Set<Subscription> subscriptions_owners) {
        this.subscriptions_owners = subscriptions_owners;
    }

    public Set<Appeal> getAppealsSent() {
        return appealsSent;
    }

    public void setAppealsSent(Set<Appeal> appealsSent) {
        this.appealsSent = appealsSent;
    }

    public Set<Appeal> getAppelsFor() {
        return appelsFor;
    }

    public void setAppelsFor(Set<Appeal> appelsFor) {
        this.appelsFor = appelsFor;
    }

	public String getReasonForRegistration() {
		return reasonForRegistration;
	}

	public void setReasonForRegistration(String reasonForRegistration) {
		this.reasonForRegistration = reasonForRegistration;
	}

	public String getPersonalDescription() {
		return personalDescription;
	}

	public void setPersonalDescription(String personalDescription) {
		this.personalDescription = personalDescription;
	}
}
