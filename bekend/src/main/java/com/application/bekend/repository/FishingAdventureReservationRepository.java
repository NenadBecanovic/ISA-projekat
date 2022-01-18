package com.application.bekend.repository;

import com.application.bekend.model.AdventureReservation;
import com.application.bekend.model.BoatReservation;
import com.application.bekend.model.FishingAdventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FishingAdventureReservationRepository extends JpaRepository<AdventureReservation, Long> {

    List<AdventureReservation> getAllByFishingAdventure_Id(Long id);
    
    AdventureReservation getFishingAdventureReservationById(Long id);
    
    List<AdventureReservation> getAdventureReservationsByFishingAdventureInstructorId(Long id);
    
   // List<AdventureReservation> getAvaibilityPeriodsByInstructorId(Long id);

    List<AdventureReservation> getAdventureReservationByGuestId(Long id);
}