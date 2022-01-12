package com.application.bekend.DTO;

public class ReportAppealAnswerDTO {
	
	private Long ownerId;
	private String ownerResponse;
	private Long guestId;
	private String guestResponse;
	private boolean isPenalty = false;
	
	public ReportAppealAnswerDTO(Long ownerId, String ownerResponse, Long guestId, String guestResponse,
			boolean isPenalty) {
		super();
		this.ownerId = ownerId;
		this.ownerResponse = ownerResponse;
		this.guestId = guestId;
		this.guestResponse = guestResponse;
		this.isPenalty = isPenalty;
	}
	
	public ReportAppealAnswerDTO() {
		
	}
	
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public String getOwnerResponse() {
		return ownerResponse;
	}
	public void setOwnerResponse(String ownerResponse) {
		this.ownerResponse = ownerResponse;
	}
	public Long getGuestId() {
		return guestId;
	}
	public void setGuestId(Long guestId) {
		this.guestId = guestId;
	}
	public String getGuestResponse() {
		return guestResponse;
	}
	public void setGuestResponse(String guestResponse) {
		this.guestResponse = guestResponse;
	}
	public boolean isPenalty() {
		return isPenalty;
	}
	public void setPenalty(boolean isPenalty) {
		this.isPenalty = isPenalty;
	}
}
