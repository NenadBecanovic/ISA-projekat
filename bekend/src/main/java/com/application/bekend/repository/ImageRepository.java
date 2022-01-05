package com.application.bekend.repository;

import com.application.bekend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> getAllByHouse_Id(Long id);

    List<Image> getAllByBoat_Id(Long id);
    
    List<Image> getAllByFishing_Adventure_Id(Long id);
}
