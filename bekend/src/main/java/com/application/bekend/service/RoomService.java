package com.application.bekend.service;

import com.application.bekend.DTO.RoomDTO;
import com.application.bekend.model.Room;
import com.application.bekend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllByHouse_Id(Long id) { return this.roomRepository.getAllByHouse_Id(id); }

    public Room save(Room room) { return this.roomRepository.save(room); }

    public Room getRoomById(Long id) { return this.roomRepository.getRoomById(id); }

    public void deleteById(Long id) { this.roomRepository.deleteById(id);}
}
