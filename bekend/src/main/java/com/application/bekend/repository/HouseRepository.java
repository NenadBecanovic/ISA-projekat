package com.application.bekend.repository;

import com.application.bekend.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    House getHouseById(Long id);
  
    void deleteById(Long id);

    House getHouseByName(String name);
}
