package com.application.bekend.service;

import com.application.bekend.model.*;
import com.application.bekend.repository.BoatRepository;
import com.application.bekend.repository.FishingAdventureRepository;
import com.application.bekend.repository.HouseRepository;
import com.application.bekend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final FishingAdventureRepository fishingAdventureRepository;
	private final HouseRepository houseRepository;
	private final BoatRepository boatRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository, FishingAdventureRepository fishingAdventureRepository, HouseRepository houseRepository, BoatRepository boatRepository) {
        this.imageRepository = imageRepository;
        this.fishingAdventureRepository = fishingAdventureRepository;
		this.houseRepository = houseRepository;
		this.boatRepository = boatRepository;
	}

    public List<Image> getAllByHouse_Id(Long id) { return imageRepository.getAllByHouse_Id(id); }

    public List<Image> getAllByBoat_Id(Long id) { return imageRepository.getAllByBoat_Id(id); }
    
    public List<Image> getAllByFishingAdventure(Long id) { return imageRepository.getAllByFishingAdventure_Id(id); }

    public Image getImageById(Long id) { return  this.imageRepository.getImageById(id); }

    public Image save(Image image) {
        return this.imageRepository.save(image);
    }

    public void delete(Long id){
        this.imageRepository.deleteById(id);
    }
    
    public void uploadAdventureImage(String newImage, Long id) throws IOException {
    	System.out.print(newImage);
		FishingAdventure fishingAdventure = fishingAdventureRepository.getById(id);
		if (newImage != null) { 
			if (!newImage.isEmpty() && newImage.startsWith("data:image")) {
				String date = new Date().toString();
				date = date.replaceAll("\\s", "");
				date = date.replaceAll("\\:", "");
				String path = "assets/" + "avantura" + id + date +".jpg";
				Base64DecodeAndSave(newImage, path);
				path = "assets/" + "avantura" + id + date +".jpg";
				Image image = new Image();
				image.setImageUrl(path);
				image.setFishingAdventure(fishingAdventure);
				imageRepository.save(image);
			}
		}
	}

	public void uploadHouseImage(String newImage, Long id) throws IOException {
		House house = this.houseRepository.getHouseById(id);
		if (newImage != null) {
			if (!newImage.isEmpty() && newImage.startsWith("data:image")) {
				String date = new Date().toString();
				date = date.replaceAll("\\s", "");
				date = date.replaceAll("\\:", "");
				String path = "assets/" + "vikendica" + id + date +".jpg";
				Base64DecodeAndSave(newImage, path);
				path = "assets/" + "vikendica" + id + date +".jpg";
				Image image = new Image();
				image.setImageUrl(path);
				image.setHouse(house);
				imageRepository.save(image);
			}
		}
	}

	public void uploadBoatImage(String newImage, Long id) throws IOException {
		Boat boat = this.boatRepository.getBoatById(id);
		if (newImage != null) {
			if (!newImage.isEmpty() && newImage.startsWith("data:image")) {
				String date = new Date().toString();
				date = date.replaceAll("\\s", "");
				date = date.replaceAll("\\:", "");
				String path = "assets/" + "brod" + id + date +".jpg";
				Base64DecodeAndSave(newImage, path);
				path = "assets/" + "brod" + id + date +".jpg";
				Image image = new Image();
				image.setImageUrl(path);
				image.setBoat(boat);
				imageRepository.save(image);
			}
		}
	}
    
    public void Base64DecodeAndSave(String base64String, String imagePath) throws FileNotFoundException, IOException {
		String part[] = base64String.split(",");
		String path = "../frontend/src/" + imagePath;
		byte[] data = Base64.getDecoder().decode(part[1]);
		System.out.println(part[1]);
		try (OutputStream stream = new FileOutputStream(path)) {
		    stream.write(data);
		}
	}

}
