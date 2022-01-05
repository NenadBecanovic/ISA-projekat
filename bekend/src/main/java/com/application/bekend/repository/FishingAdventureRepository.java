package com.application.bekend.repository;

import com.application.bekend.model.FishingAdventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishingAdventureRepository extends JpaRepository<FishingAdventure, Long> {
}
