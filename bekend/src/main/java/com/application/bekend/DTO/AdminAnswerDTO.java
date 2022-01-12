package com.application.bekend.DTO;

public class AdminAnswerDTO {
	
	private String clientResponse;
	private String ownerResponse;
	
	public AdminAnswerDTO(String clientResponse, String ownerResponse) {
		super();
		this.clientResponse = clientResponse;
		this.ownerResponse = ownerResponse;
	}
	
	public AdminAnswerDTO() {
		
	}

	public String getClientResponse() {
		return clientResponse;
	}

	public void setClientResponse(String clientResponse) {
		this.clientResponse = clientResponse;
	}

	public String getOwnerResponse() {
		return ownerResponse;
	}

	public void setOwnerResponse(String ownerResponse) {
		this.ownerResponse = ownerResponse;
	}
}
