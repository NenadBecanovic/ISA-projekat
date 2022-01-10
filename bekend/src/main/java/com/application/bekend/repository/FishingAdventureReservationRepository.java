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
    
   // @Query("select a.guestId from AdventureReservation a join fetch a.fishingAdventure h where h.instructorId = ?2 and a.startDate <= ?1 and a.endDate >= ?1")
    //Long getAllByCurrentGuest(Date currentDateAndTime, Long instructorId);
  /*

    HouseReservation getHouseReservationById(Long id);

    void deleteById(Long id);

    List<HouseReservation> getHouseReservationsByGuestId(Long id);*/
}