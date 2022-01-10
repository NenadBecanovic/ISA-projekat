package com.application.bekend.service;

import com.application.bekend.model.Report;
import com.application.bekend.repository.ReportRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report save(Report report) { return this.reportRepository.save(report); }

    public Report getReportByHouseReservationId(Long id) { return this.reportRepository.getByHouseReservation_Id(id); }

    public Report getReportByBoatReservationId(Long id) { return this.reportRepository.getByBoatReservation_Id(id); }
}
