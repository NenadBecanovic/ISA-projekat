package com.application.bekend.service;

import com.application.bekend.DTO.EmailDTO;
import com.application.bekend.DTO.ReportAppealAnswerDTO;
import com.application.bekend.model.Report;
import com.application.bekend.model.RequestForAccountDeleting;
import com.application.bekend.repository.ReportRepository;
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
    
    public boolean sendReportResponse(Long id, ReportAppealAnswerDTO answerDTO) {
    	Report report = this.reportRepository.findReportById(id);
  /*  	this.emailService.sendAnswerEmail(new EmailDTO("Odgovor na izveštaj rezervacije", clientMessage, request.getUser().getEmail()));
    	request.setAnswered(true);
    	this.requestForAccountDeletingService.save(request);*/
    	return true;
    }
}
