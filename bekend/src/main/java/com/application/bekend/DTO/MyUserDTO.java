package com.application.bekend.DTO;

public class MyUserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private String authority;
    private AddressDTO addressDTO;
    private String phoneNumber;
    private String reasonForRegistration;

    public MyUserDTO(String firstName, String lastName, String email, String password, String username, String authority, AddressDTO addressDTO, String phoneNumber, String reasonForRegistration) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.authority = authority;
        this.addressDTO = addressDTO;
        this.phoneNumber = phoneNumber;
        this.reasonForRegistration = reasonForRegistration;
    }

    public MyUserDTO() {
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

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber;}

    public String getReasonForRegistration() { return reasonForRegistration; }

    public void setReasonForRegistration(String reasonForRegistration) { this.reasonForRegistration = reasonForRegistration; }
}
