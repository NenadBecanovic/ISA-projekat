package com.application.bekend.repository;

import com.application.bekend.model.AdventureReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FishingAdventureReservationRepository extends JpaRepository<AdventureReservation, Long> {

    List<AdventureReservation> getAllByFishingAdventure_Id(Long id);

  /*  List<HouseReservation> findHouseReservationByGuestId(Long id);

    HouseReservation getHouseReservationById(Long id);

    void deleteById(Long id);

    List<HouseReservation> getHouseReservationsByGuestId(Long id);*/
}