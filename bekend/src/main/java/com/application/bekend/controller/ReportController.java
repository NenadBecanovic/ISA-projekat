package com.application.bekend.controller;

import com.application.bekend.DTO.ReportDTO;
import com.application.bekend.model.*;
import com.application.bekend.service.BoatReservationService;
import com.application.bekend.service.FishingAdventureReservationService;
import com.application.bekend.service.HouseReservationService;
import com.application.bekend.service.MyUserService;
import com.application.bekend.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("api/report")
@EnableTransactionManagement
public class ReportController {

    private final ReportService reportService;
    private final HouseReservationService houseReservationService;
    private final BoatReservationService boatReservationService;
    private final MyUserService myUserService;
    private final FishingAdventureReservationService adventureReservationService;

    public ReportController(ReportService reportService, HouseReservationService houseReservationService, BoatReservationService boatReservationService, MyUserService myUserService,
    		FishingAdventureReservationService adventureReservationService) {
        this.reportService = reportService;
        this.houseReservationService = houseReservationService;
        this.boatReservationService = boatReservationService;
        this.myUserService = myUserService;
        this.adventureReservationService = adventureReservationService;
    }

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<Report> add(@RequestBody ReportDTO dto) {
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

        this.reportService.save(report);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getReportByHouseReservationId/{id}")
    public ResponseEntity<ReportDTO> getReportByHouseReservationId(@PathVariable("id") Long id) {
        Report report = this.reportService.getReportByHouseReservationId(id);

        if (report == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ReportDTO reportDTO = new ReportDTO(report.getId(), report.getComment(), report.isMissedReservation(), report.isPenaltyProposal());
        reportDTO.setHouseReservationId(id);

        return new ResponseEntity<>(reportDTO, HttpStatus.OK);
    }

    @GetMapping("/getReportByBoatReservationId/{id}")
    public ResponseEntity<ReportDTO> getReportByBoatReservationId(@PathVariable("id") Long id) {
        Report report = this.reportService.getReportByBoatReservationId(id);

        if (report == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ReportDTO reportDTO = new ReportDTO(report.getId(), report.getComment(), report.isMissedReservation(), report.isPenaltyProposal());
        reportDTO.setHouseReservationId(id);

        return new ResponseEntity<>(reportDTO, HttpStatus.OK);
    }
}
