package com.application.bekend.DTO;

public class HouseDTO {

    private Long id;
    private String name;
//    private Set<Room> rooms = new HashSet<Room>();
    private AddressDTO address;
    private String promoDescription;
    //    private Set<HouseReservation> courses = new HashSet<HouseReservation>();
    private String behaviourRules;
    private float pricePerDay;
//    private Set<AdditionalServices> services = new HashSet<AdditionalServices>();
    private boolean isCancalletionFree;
    private int cancalletionFee;
//    private MyUser owner;

    public HouseDTO(Long id, String name, AddressDTO addressDTO, String promoDescription, String behaviourRules, float pricePerDay,
                    boolean isCancalletionFree, int cancalletionFee) {
        this.id = id;
        this.name = name;
        this.address = addressDTO;
        this.promoDescription = promoDescription;
        this.behaviourRules = behaviourRules;
        this.pricePerDay = pricePerDay;
        this.isCancalletionFree = isCancalletionFree;
        this.cancalletionFee = cancalletionFee;
    }

    public HouseDTO() {
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

}
