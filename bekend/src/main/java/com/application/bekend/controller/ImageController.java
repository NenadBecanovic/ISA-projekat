package com.application.bekend.controller;

import com.application.bekend.DTO.ImageDTO;
import com.application.bekend.DTO.RoomDTO;
import com.application.bekend.model.Image;
import com.application.bekend.model.Room;
import com.application.bekend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/image")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/getAllByHouseId/{id}")
    public ResponseEntity<List<ImageDTO>> getAllByHouseId(@PathVariable("id") Long id){
        List<Image> images = this.imageService.getAllByHouse_Id(id);
        List<ImageDTO> imagesDTOS = new ArrayList<>();

        for (Image i: images) {
            ImageDTO imageDTO = new ImageDTO(i.getId(), i.getImageUrl());
            imagesDTOS.add(imageDTO);
        }

        return new ResponseEntity<>(imagesDTOS, HttpStatus.OK);
    }
}
