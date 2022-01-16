package com.application.bekend.service;

import com.application.bekend.DTO.EmailDTO;
import com.application.bekend.DTO.ReportAppealAnswerDTO;
import com.application.bekend.DTO.ReportInfoDTO;
import com.application.bekend.model.MyUser;
import com.application.bekend.model.Report;
import com.application.bekend.model.RequestForAccountDeleting;
import com.application.bekend.repository.ReportRepository;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final MyUserService myUserService;
    private final EmailService emailService;

    public ReportService(ReportRepository reportRepository, MyUserService myUserService, EmailService emailService) {
        this.reportRepository = reportRepository;
        this.myUserService = myUserService;
        this.emailService = emailService;
    }

    public Report save(Report report) { return this.reportRepository.save(report); }

    public Report getReportByHouseReservationId(Long id) { return this.reportRepository.getByHouseReservation_Id(id); }

    public Report getReportByBoatReservationId(Long id) { return this.reportRepository.getByBoatReservation_Id(id); }
    
    public List<ReportInfoDTO> getAllReports(){
    	List<Report> allReports = this.reportRepository.findAll();
    	List<ReportInfoDTO> allReportsDTO = new ArrayList<ReportInfoDTO>();
    	for(Report r: allReports) {
    		if(r.getBoatReservation() != null) {
    			String ownerName = r.getBoatReservation().getBoat().getOwner().getFirstName() + " " + r.getBoatReservation().getBoat().getOwner().getLastName();
    			String guestName = r.getBoatReservation().getGuest().getFirstName() + " " + r.getBoatReservation().getGuest().getLastName();
    			ReportInfoDTO reportDTO = new ReportInfoDTO(r.getId(), r.getComment(),r.isPenaltyProposal(), r.getBoatReservation().getBoat().getOwner().getId(), ownerName, r.getBoatReservation().getGuest().getId(), guestName, r.isReviewed());
    			allReportsDTO.add(reportDTO);
    		}else if(r.getHouseReservation() != null) {
    			String ownerName = r.getHouseReservation().getHouse().getOwner().getFirstName() + " " + r.getHouseReservation().getHouse().getOwner().getLastName();
    			String guestName = r.getHouseReservation().getGuest().getFirstName() + " " + r.getHouseReservation().getGuest().getLastName();
    			ReportInfoDTO reportDTO = new ReportInfoDTO(r.getId(), r.getComment(),r.isPenaltyProposal(), r.getHouseReservation().getHouse().getOwner().getId(), ownerName, r.getHouseReservation().getGuest().getId(), guestName, r.isReviewed());
    			allReportsDTO.add(reportDTO);
    		}else {
    			String ownerName = r.getAdventureReservation().getFishingAdventure().getInstructor().getFirstName() + " " + r.getAdventureReservation().getFishingAdventure().getInstructor().getLastName();
    			String guestName = r.getAdventureReservation().getGuest().getFirstName() + " " + r.getAdventureReservation().getGuest().getLastName();
    			ReportInfoDTO reportDTO = new ReportInfoDTO(r.getId(), r.getComment(),r.isPenaltyProposal(), r.getAdventureReservation().getFishingAdventure().getInstructor().getId(), ownerName, r.getAdventureReservation().getGuest().getId(), guestName, r.isReviewed());
    			allReportsDTO.add(reportDTO);
    		}
    	}
    	
    	return allReportsDTO;
    }
    
    public boolean sendReportResponse(Long id, ReportAppealAnswerDTO answerDTO) throws MessagingException {
    	Report report = this.reportRepository.findReportById(id);
    	MyUser owner = this.myUserService.findUserById(answerDTO.getOwnerId());
    	this.emailService.sendAnswerEmail(new EmailDTO("Odgovor na izveštaj rezervacije", answerDTO.getOwnerResponse(), owner.getEmail()));
    	MyUser guest = this.myUserService.findUserById(answerDTO.getGuestId());
    	this.emailService.sendAnswerEmail(new EmailDTO("Poruka o izveštaju vlasnika", answerDTO.getGuestResponse(), guest.getEmail()));
    	if(answerDTO.getIsPenalty()) {
    		guest.setPenalties(guest.getPenalties() + 1);
    		this.myUserService.save(guest);
    	}
    	report.setReviewed(true);
    	this.reportRepository.save(report);
    	return true;
    }

	public void delete(Long id){
		reportRepository.deleteById(id);
	}
}
