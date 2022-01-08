package com.application.bekend.repository;

import com.application.bekend.model.NavigationEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavigationEquipmentRepository extends JpaRepository<NavigationEquipment, Long> {

    NavigationEquipment getNavigationEquipmentByBoatId(Long id);
}
