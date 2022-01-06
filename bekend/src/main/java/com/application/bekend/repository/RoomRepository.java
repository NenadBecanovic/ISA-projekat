package com.application.bekend.repository;

import com.application.bekend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> getAllByHouse_Id(Long id);

    Room getRoomById(Long id);

    void deleteById(Long id);
}
