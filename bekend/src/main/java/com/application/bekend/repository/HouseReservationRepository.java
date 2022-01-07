package com.application.bekend.repository;

import com.application.bekend.model.HouseReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseReservationRepository extends JpaRepository<HouseReservation, Long> {

    List<HouseReservation> getAllByHouse_Id(Long id);

    List<HouseReservation> findHouseReservationByGuestId(Long id);

    HouseReservation getHouseReservationById(Long id);

    void deleteById(Long id);

    List<HouseReservation> getHouseReservationsByGuestId(Long id);
}
