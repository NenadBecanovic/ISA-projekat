package com.application.bekend.repository;

import com.application.bekend.model.House;
import com.application.bekend.model.HouseReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseReservationsRepository extends JpaRepository<HouseReservation, Long> {

    List<HouseReservation> getAllByHouse_Id(Long id);
}
