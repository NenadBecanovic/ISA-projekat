package com.application.bekend.repository;

import com.application.bekend.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    Report getByHouseReservation_Id(Long id);

    Report getByBoatReservation_Id(Long id);
    
    Report getByAdventureReservation_Id(Long id);
    
    Report findReportById(Long id);
}
