package com.application.bekend.DTO;

public class ReportDTO {

    private Long id;
    private String comment;
    private boolean missedReservation;
    private boolean penaltyProposal;
    private Long houseReservationId;
    private Long boatReservationId;
    private Long adventureReservationId;
    private boolean isReviwed = false;

    public ReportDTO(Long id, String comment, boolean showedUp, boolean penaltyProposal, Long houseReservationId, Long boatReservationId, Long adventureReservationId) {
        this.id = id;
        this.comment = comment;
        this.missedReservation = showedUp;
        this.penaltyProposal = penaltyProposal;
        this.houseReservationId = houseReservationId;
        this.boatReservationId = boatReservationId;
        this.adventureReservationId = adventureReservationId;
    }

    public ReportDTO(Long id, String comment, boolean showedUp, boolean penaltyProposal) {
        this.id = id;
        this.comment = comment;
        this.missedReservation = showedUp;
        this.penaltyProposal = penaltyProposal;
    }

    public ReportDTO() {}

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

    public boolean isMissedReservation() {
        return missedReservation;
    }

    public void setMissedReservation(boolean missedReservation) {
        this.missedReservation = missedReservation;
    }

    public boolean isPenaltyProposal() {
        return penaltyProposal;
    }

    public void setPenaltyProposal(boolean penaltyProposal) {
        this.penaltyProposal = penaltyProposal;
    }

    public Long getHouseReservationId() {
        return houseReservationId;
    }

    public void setHouseReservationId(Long houseReservationId) {
        this.houseReservationId = houseReservationId;
    }

    public Long getBoatReservationId() {
        return boatReservationId;
    }

    public void setBoatReservationId(Long boatReservationId) {
        this.boatReservationId = boatReservationId;
    }

	public Long getAdventureReservationId() {
		return adventureReservationId;
	}

	public void setAdventureReservationId(Long adventureReservationId) {
		this.adventureReservationId = adventureReservationId;
	}

	public boolean getIsReviwed() {
		return isReviwed;
	}

	public void setReviwed(boolean isReviwed) {
		this.isReviwed = isReviwed;
	}
}
