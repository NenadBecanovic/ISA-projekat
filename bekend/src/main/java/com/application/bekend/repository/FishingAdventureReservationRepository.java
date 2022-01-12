package com.application.bekend.repository;

import com.application.bekend.model.AdventureReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FishingAdventureReservationRepository extends JpaRepository<AdventureReservation, Long> {

    List<AdventureReservation> getAllByFishingAdventure_Id(Long id);
    
    AdventureReservation getFishingAdventureReservationById(Long id);
    
    @Query("select a from AdventureReservation a join fetch a.fishingAdventure f where f.instructor = ?1")
    List<AdventureReservation> getAdventureReservationsByInstructorId(Long id);

}