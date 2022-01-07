package com.application.bekend.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.bekend.model.FishingAdventure;
import com.application.bekend.repository.FishingAdventureRepository;
import com.application.bekend.model.House;
import com.application.bekend.model.Image;

import org.springframework.data.domain.Sort;

@Service
public class FishingAdventureService {

    private final FishingAdventureRepository fishingAdventureRepository;

    @Autowired
    public FishingAdventureService(FishingAdventureRepository fishingAdventureRepository) {
        this.fishingAdventureRepository = fishingAdventureRepository;
    }

    public FishingAdventure getFishingAdventureById(Long id){ return fishingAdventureRepository.getFishingAdventureById(id); }
    
    public List<FishingAdventure> getFishingAdventuresByInstructor(Long id){ return fishingAdventureRepository.getFishingAdventuresByInstructor(id); }


    public List<FishingAdventure> findAll(){return this.fishingAdventureRepository.findAll();}
    
    public void uploadImage(String newImage, Long id) throws IOException {
		FishingAdventure fishingAdventure = fishingAdventureRepository.getById(id);
		if (newImage != null) { 
			if (!newImage.isEmpty() && newImage.startsWith("data:image")) {
				String path = "assets/" + "avantura" + id +".jpg";
				Base64DecodeAndSave(newImage, path);
				path = "./" + "assets/" + "avantura" + id +".jpg";
				Image image = new Image();
				image.setImageUrl(path);
				image.setFishingAdventure(fishingAdventure);
				fishingAdventure.addImage(image);
			}
		}
		fishingAdventureRepository.save(fishingAdventure);
	}
    
    public void Base64DecodeAndSave(String base64String, String imagePath) throws FileNotFoundException, IOException {
		String part[] = base64String.split(",");
		String path = "./frontend/src/" + imagePath;
		byte[] data = Base64.getDecoder().decode(part[1]);
		System.out.println(part[1]);
		try (OutputStream stream = new FileOutputStream(path)) {
		    stream.write(data);
		}
	}
}
