package com.application.bekend.service;

import com.application.bekend.model.Image;
import com.application.bekend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> getAllByHouse_Id(Long id) { return imageRepository.getAllByHouse_Id(id); }

    public List<Image> getAllByBoat_Id(Long id) { return imageRepository.getAllByBoat_Id(id); }
    
    public List<Image> getAllByFishing_Adventure_Id(Long id) { return imageRepository.getAllByFishing_Adventure_Id(id); }
}
