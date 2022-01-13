package com.application.bekend.DTO;

public class ReportInfoDTO {

	private Long id;
	private String comment;
	private boolean penaltyProposal;
	private Long ownerId;
	private String ownerFullName;
	private Long guestId;
	private String guestFullName;
	private boolean isReviewed;
	
	public ReportInfoDTO(Long id, String comment, boolean penaltyProposal, Long ownerId, String ownerFullName,
			Long guestId, String guestFullName, boolean isReviewed) {
		super();
		this.id = id;
		this.comment = comment;
		this.penaltyProposal = penaltyProposal;
		this.ownerId = ownerId;
		this.ownerFullName = ownerFullName;
		this.guestId = guestId;
		this.guestFullName = guestFullName;
		this.isReviewed = isReviewed;
	}
	
	public ReportInfoDTO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean getPenaltyProposal() {
		return penaltyProposal;
	}

	public void setPenaltyProposal(boolean penaltyProposal) {
		this.penaltyProposal = penaltyProposal;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerFullName() {
		return ownerFullName;
	}

	public void setOwnerFullName(String ownerFullName) {
		this.ownerFullName = ownerFullName;
	}

	public Long getGuestId() {
		return guestId;
	}

	public void setGuestId(Long guestId) {
		this.guestId = guestId;
	}

	public String getGuestFullName() {
		return guestFullName;
	}

	public void setGuestFullName(String guestFullName) {
		this.guestFullName = guestFullName;
	}

	public boolean getIsReviewed() {
		return isReviewed;
	}

	public void setIsReviewed(boolean isReviewed) {
		this.isReviewed = isReviewed;
	}
}
