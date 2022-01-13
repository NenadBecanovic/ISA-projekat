package com.application.bekend.DTO;

public class NewUserRequestDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String reasonForRegistration;
	
	public NewUserRequestDTO(Long id, String firstName, String lastName, String reasonForRegistration) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.reasonForRegistration = reasonForRegistration;
	}
	
	public NewUserRequestDTO() {
		
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

	public String getReasonForRegistration() {
		return reasonForRegistration;
	}

	public void setReasonForRegistration(String reasonForRegistration) {
		this.reasonForRegistration = reasonForRegistration;
	}
}
