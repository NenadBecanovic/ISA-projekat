package com.application.bekend.controller;

import com.application.bekend.DTO.ReportAppealAnswerDTO;
import com.application.bekend.DTO.ReportDTO;
import com.application.bekend.DTO.ReportInfoDTO;
import com.application.bekend.model.*;
import com.application.bekend.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.mail.MessagingException;

@RestController
@RequestMapping("api/report")
@EnableTransactionManagement
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_HOUSE_OWNER') or hasRole('ROLE_BOAT_OWNER') or hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity<Report> add(@RequestBody ReportDTO dto) {
        this.reportService.addReport(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getReportByHouseReservationId/{id}")
    public ResponseEntity<ReportDTO> getReportByHouseReservationId(@PathVariable("id") Long id) {
        ReportDTO report = this.reportService.getReportDTOByHouseReservationId(id);

        if (report == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @GetMapping("/getReportByBoatReservationId/{id}")
    public ResponseEntity<ReportDTO> getReportByBoatReservationId(@PathVariable("id") Long id) {
        ReportDTO report = this.reportService.getReportDTOByBoatReservationId(id);

        if (report == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
    
    @GetMapping("/getAllReports")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<List<ReportInfoDTO>> getAllReports() {
        List<ReportInfoDTO> allReports = this.reportService.getAllReports();
        return new ResponseEntity<>(allReports, HttpStatus.OK);
    }
    
    @PutMapping("/sendReportResponse/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Boolean> sendReportResponse(@PathVariable("id") Long id, @RequestBody ReportAppealAnswerDTO answerDTO) throws MessagingException{
    	boolean isAnswered = this.reportService.sendReportResponse(id, answerDTO);
        return new ResponseEntity<>(isAnswered, HttpStatus.OK);
    }
}
