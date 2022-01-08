package com.application.bekend.repository;

import com.application.bekend.model.BoatReservation;
import com.application.bekend.model.HouseReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoatReservationRepository extends JpaRepository<BoatReservation, Long> {

    List<BoatReservation> getAllByBoat_Id(Long id);

    BoatReservation getBoatReservationById(Long id);

    void deleteById(Long id);

    List<BoatReservation> getBoatReservationsByGuestId(Long id);
}
