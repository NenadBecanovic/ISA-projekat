package com.application.bekend.service;

import com.application.bekend.DTO.EmailDTO;
import com.application.bekend.DTO.ReportAppealAnswerDTO;
import com.application.bekend.DTO.ReportDTO;
import com.application.bekend.DTO.ReportInfoDTO;
import com.application.bekend.model.AdventureReservation;
import com.application.bekend.model.BoatReservation;
import com.application.bekend.model.HouseReservation;
import com.application.bekend.model.MyUser;
import com.application.bekend.model.Report;
import com.application.bekend.repository.ReportRepository;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;
    private final MyUserService myUserService;
    private final EmailService emailService;
    private final HouseReservationService houseReservationService;
    private final BoatReservationService boatReservationService;
    private final FishingAdventureReservationService adventureReservationService;

    public ReportService(ReportRepository reportRepository, MyUserService myUserService, EmailService emailService, HouseReservationService houseReservationService, BoatReservationService boatReservationService,
    		FishingAdventureReservationService adventureReservationService) {
        this.reportRepository = reportRepository;
        this.myUserService = myUserService;
        this.emailService = emailService;
        this.houseReservationService = houseReservationService;
        this.boatReservationService = boatReservationService;
        this.adventureReservationService = adventureReservationService;
    }

    public Report save(Report report) { return this.reportRepository.save(report); }
    
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
	
	public void addReport(ReportDTO dto) {
		Report report = new Report(dto.getId(), dto.getComment(), dto.isMissedReservation(), dto.isPenaltyProposal());

        if (dto.getHouseReservationId() != null && dto.getHouseReservationId() != 0) {
            HouseReservation houseReservation = this.houseReservationService.getHouseReservationById(dto.getHouseReservationId());
            report.setHouseReservation(houseReservation);

            if (report.isMissedReservation())
            {
                MyUser user = this.myUserService.findUserByHouseReservationId(houseReservation.getId());
                int penalities = user.getPenalties();
                penalities = penalities + 1;
                user.setPenalties(penalities);
                this.myUserService.save(user);
            }
        } else if (dto.getBoatReservationId() != null && dto.getBoatReservationId() != 0) {
            BoatReservation boatReservation = this.boatReservationService.getBoatReservationById(dto.getBoatReservationId());
            report.setBoatReservation(boatReservation);

            if (report.isMissedReservation())
            {
                MyUser user = this.myUserService.findUserByBoatReservationId(boatReservation.getId());
                int penalities = user.getPenalties();
                penalities = penalities + 1;
                user.setPenalties(penalities);
                this.myUserService.save(user);
            }
        } else if (dto.getAdventureReservationId() != null && dto.getAdventureReservationId() != 0) {
            AdventureReservation adventureReservation = this.adventureReservationService.getFishingAdventureReservationById(dto.getAdventureReservationId());
            report.setAdventureReservation(adventureReservation);
            adventureReservation.setHasReport(true);
            if (report.isMissedReservation())
            {
                MyUser user = this.myUserService.findUserByAdventureReservationId(adventureReservation.getId());
                int penalities = user.getPenalties();
                penalities = penalities + 1;
                user.setPenalties(penalities);
                this.myUserService.save(user);
            }
        }

        this.save(report);
	}
	
	public Report getReportByHouseReservationId(Long id) { return this.reportRepository.getByHouseReservation_Id(id);}
	
	public Report getReportByBoatReservationId(Long id) { return this.reportRepository.getByBoatReservation_Id(id);}
	
	public Report getReportByAdventureReservationId(Long id) { return this.reportRepository.getByAdventureReservation_Id(id);}
	
    public ReportDTO getReportDTOByHouseReservationId(Long id) {
        Report report = this.reportRepository.getByHouseReservation_Id(id);

        if (report == null)
        {
            return null;
        }

        ReportDTO reportDTO = new ReportDTO(report.getId(), report.getComment(), report.isMissedReservation(), report.isPenaltyProposal());
        reportDTO.setHouseReservationId(id);

        return reportDTO;
    }
    
    public ReportDTO getReportDTOByBoatReservationId(Long id) {
        Report report = this.reportRepository.getByBoatReservation_Id(id);

        if (report == null)
        {
            return null;
        }

        ReportDTO reportDTO = new ReportDTO(report.getId(), report.getComment(), report.isMissedReservation(), report.isPenaltyProposal());
        reportDTO.setHouseReservationId(id);

        return reportDTO;
    }
}
