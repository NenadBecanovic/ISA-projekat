package com.application.bekend.repository;

import com.application.bekend.model.AdditionalServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AdditionalServicesRepository extends JpaRepository<AdditionalServices, Long> {

    @Query("select a from AdditionalServices a join fetch a.houses h where h.id = ?1")
    List<AdditionalServices> getAllByHouseId(Long id);

    @Query("select a from AdditionalServices a join fetch a.boats b where b.id = ?1")
    List<AdditionalServices> getAllByBoatId(Long id);

    @Query("select a from AdditionalServices a join fetch a.houseReservationsServices h where h.id = ?1")
    Set<AdditionalServices> getAllByHouseReservationId(Long id);

    AdditionalServices getAdditionalServicesById(Long id);
    
    @Query("select a from AdditionalServices a join fetch a.fishingAdventures h where h.id = ?1")
    List<AdditionalServices> getAllByFishingAdventureId(Long id);

}
