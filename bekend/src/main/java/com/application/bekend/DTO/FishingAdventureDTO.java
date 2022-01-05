package com.application.bekend.DTO;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.application.bekend.model.AdditionalServices;
import com.application.bekend.model.Address;
import com.application.bekend.model.AdventureReservation;
import com.application.bekend.model.Image;
import com.application.bekend.model.MyUser;

public class FishingAdventureDTO {

    private Long id;
    private String name;
    private AddressDTO address;
    private String promoDescription;
    private int capacity;
    private String fishingEquipment;
    private String behaviourRules;
    private float pricePerHour;
    private boolean isCancalletionFree;
    private int cancalletionFee;
    
	public FishingAdventureDTO(Long id, String name, AddressDTO address, String promoDescription, int capacity,
			String fishingEquipment, String behaviourRules, float pricePerHour, boolean isCancalletionFree,
			int cancalletionFee) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.promoDescription = promoDescription;
		this.capacity = capacity;
		this.fishingEquipment = fishingEquipment;
		this.behaviourRules = behaviourRules;
		this.pricePerHour = pricePerHour;
		this.isCancalletionFree = isCancalletionFree;
		this.cancalletionFee = cancalletionFee;
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
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
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
	public boolean isCancalletionFree() {
		return isCancalletionFree;
	}
	public void setCancalletionFree(boolean isCancalletionFree) {
		this.isCancalletionFree = isCancalletionFree;
	}
	public int getCancalletionFee() {
		return cancalletionFee;
	}
	public void setCancalletionFee(int cancalletionFee) {
		this.cancalletionFee = cancalletionFee;
	}
    
}
