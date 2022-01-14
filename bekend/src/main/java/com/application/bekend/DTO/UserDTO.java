package com.application.bekend.DTO;

public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String authority;
    private AddressDTO addressDTO;
    private String phoneNumber;
    private String reasonForRegistration;
    private int penalties;
    private String personalDescription;
    private double grade;

    public UserDTO(Long id, String firstName, String lastName, String email, String username, String authority, AddressDTO addressDTO, String phoneNumber, String reasonForRegistration, int penalties, String personalDescription) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.authority = authority;
        this.addressDTO = addressDTO;
        this.phoneNumber = phoneNumber;
        this.reasonForRegistration = reasonForRegistration;
        this.penalties = penalties;
        this.personalDescription = personalDescription;
    }

    public UserDTO() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReasonForRegistration() {
        return reasonForRegistration;
    }

    public void setReasonForRegistration(String reasonForRegistration) {
        this.reasonForRegistration = reasonForRegistration;
    }

    public int getPenalties() {
        return penalties;
    }

    public void setPenalties(int penalties) {
        this.penalties = penalties;
    }

    public String getPersonalDescription() {
        return personalDescription;
    }

    public void setPersonalDescription(String personalDescription) {
        this.personalDescription = personalDescription;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
