package com.application.bekend.service;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import com.application.bekend.DTO.AdditionalServicesDTO;
import com.application.bekend.DTO.AddressDTO;
import com.application.bekend.DTO.AdventureReservationDTO;
import com.application.bekend.DTO.FishingAdventureDTO;
import com.application.bekend.DTO.FishingAdventureInstructorInfoDTO;
import com.application.bekend.DTO.ImageDTO;
import com.application.bekend.DTO.NewFishingAdventureDTO;
import com.application.bekend.model.AdditionalServices;
import com.application.bekend.model.Address;
import com.application.bekend.model.AdventureReservation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.application.bekend.model.FishingAdventure;
import com.application.bekend.repository.FishingAdventureRepository;
import com.application.bekend.model.House;
import com.application.bekend.model.Image;
import com.application.bekend.model.MyUser;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
@Transactional
public class FishingAdventureService {

	private final FishingAdventureRepository fishingAdventureRepository;
	private final FishingAdventureReservationService fishingAdventureReservationService;
	private final AddresService addressService;
	private final AdditionalServicesService additionalServicesService;
	private final ModelMapper modelMapper;
	private final MyUserService myUserService;

	@Autowired
	public FishingAdventureService(FishingAdventureRepository fishingAdventureRepository, AddresService addressService,
			AdditionalServicesService additionalServicesService,
			FishingAdventureReservationService fishingAdventureReservationService, ModelMapper modelMapper,
			MyUserService myUserService) {
		this.fishingAdventureRepository = fishingAdventureRepository;
		this.addressService = addressService;
		this.additionalServicesService = additionalServicesService;
		this.fishingAdventureReservationService = fishingAdventureReservationService;
		this.modelMapper = modelMapper;
		this.myUserService = myUserService;
	}

	public FishingAdventure getFishingAdventureById(Long id) {
		return fishingAdventureRepository.getFishingAdventureById(id);
	}

	public List<FishingAdventure> getFishingAdventuresByInstructor(Long id) {
		return fishingAdventureRepository.getFishingAdventuresByInstructor_Id(id);
	}

	public List<FishingAdventure> findAll() {
		return this.fishingAdventureRepository.findAll();
	}

	public FishingAdventure save(FishingAdventure fishingAdventure) {
		return this.fishingAdventureRepository.save(fishingAdventure);
	}

	public FishingAdventureInstructorInfoDTO getFishingAdventureInstructor(Long adventureId) {
		FishingAdventure adventure = this.fishingAdventureRepository.getById(adventureId);
		MyUser instructor = adventure.getInstructor();
		return new FishingAdventureInstructorInfoDTO(instructor.getId(), instructor.getFirstName(),
				instructor.getLastName(), instructor.getPhoneNumber(), instructor.getEmail(),
				instructor.getPersonalDescription());
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		FishingAdventure fishingAdventure = this.getFishingAdventureById(id);
		fishingAdventure.setDeleted(true);
		this.save(fishingAdventure);
	}

	public boolean saveReservation(AdventureReservationDTO adventureReservationDTO) throws MessagingException {
		FishingAdventure fishingAdventure = this.getFishingAdventureById(adventureReservationDTO.getAdventureId());
		fishingAdventure = this.fishingAdventureReservationService.saveReservation(fishingAdventure,
				adventureReservationDTO);
		if (fishingAdventure == null) {
			return false;
		}
		this.save(fishingAdventure);
		return true;
	}

	public boolean saveUnavailablePeriod(Long instructorId, AdventureReservationDTO adventureReservationDTO)
			throws MessagingException {
		// TODO Auto-generated method stub
		List<FishingAdventure> fishingAdventures = this.getFishingAdventuresByInstructor(instructorId);
		for (FishingAdventure fishingAdventure : fishingAdventures) {
			adventureReservationDTO.setAdventureId(fishingAdventure.getId());
			if (!saveReservation(adventureReservationDTO)) {
				return false;
			}
		}
		return true;
	}

	public List<AdventureReservationDTO> getAllActionsByInstructorId(Long id) {
		List<FishingAdventure> allAdventures = this.getFishingAdventuresByInstructor(id);
		List<AdventureReservationDTO> allActions = new ArrayList<AdventureReservationDTO>();
		for (FishingAdventure a : allAdventures) {
			List<AdventureReservationDTO> adventureActions = this.fishingAdventureReservationService
					.getAllActionsByAdventureId(a.getId());
			for (AdventureReservationDTO action : adventureActions) {
				allActions.add(action);
			}
		}
		return allActions;
	}

	public FishingAdventureDTO getFishingAdventureDTOById(Long id) {
		FishingAdventure fishingAdventure = this.getFishingAdventureById(id);
		if(!fishingAdventure.isDeleted()) {
			AddressDTO addressDTO = new AddressDTO(fishingAdventure.getAddress().getId(),
					fishingAdventure.getAddress().getStreet(), fishingAdventure.getAddress().getCity(),
					fishingAdventure.getAddress().getState(), fishingAdventure.getAddress().getLongitude(),
					fishingAdventure.getAddress().getLatitude(), fishingAdventure.getAddress().getPostalCode());
	
			FishingAdventureDTO fishingAdventureDTO = new FishingAdventureDTO(fishingAdventure.getId(),
					fishingAdventure.getName(), addressDTO, fishingAdventure.getPromoDescription(),
					fishingAdventure.getCapacity(), fishingAdventure.getFishingEquipment(),
					fishingAdventure.getBehaviourRules(), fishingAdventure.getPricePerHour(),
					fishingAdventure.isCancalletionFree(), fishingAdventure.getCancalletionFee(),
					fishingAdventure.getGrade(), fishingAdventure.getNumberOfReviews());
			fishingAdventureDTO.setInstructorId(fishingAdventure.getInstructor().getId());
	
			return fishingAdventureDTO;
		}
		return null;
	}

	public List<FishingAdventureDTO> getFishingAdventuresDTOSByInstructor(Long id) {
		List<FishingAdventure> fishingAdventures = this.getFishingAdventuresByInstructor(id);
		List<FishingAdventureDTO> instructorFishingAdventures = new ArrayList<FishingAdventureDTO>();

		for (FishingAdventure f : fishingAdventures) {
			if (!f.isDeleted()) {
				AddressDTO addressDTO = new AddressDTO(f.getAddress().getId(), f.getAddress().getStreet(),
						f.getAddress().getCity(), f.getAddress().getState(), f.getAddress().getLongitude(),
						f.getAddress().getLatitude(), f.getAddress().getPostalCode());
				Set<ImageDTO> dtoSet = new HashSet<>();
				for (Image i : f.getImages()) {
					ImageDTO imageDTO = modelMapper.map(i, ImageDTO.class);
					dtoSet.add(imageDTO);
				}
				FishingAdventureDTO dto = new FishingAdventureDTO(f.getId(), f.getName(), addressDTO,
						f.getPromoDescription(), f.getCapacity(), f.getFishingEquipment(), f.getBehaviourRules(),
						f.getPricePerHour(), f.isCancalletionFree(), f.getCancalletionFee(), f.getGrade(),
						f.getNumberOfReviews());
				dto.setImages(dtoSet);
				instructorFishingAdventures.add(dto);
			}
		}

		return instructorFishingAdventures;
	}

	public Long saveAdventure(NewFishingAdventureDTO newFishingAdventure) throws IOException {
		Address address = new Address(newFishingAdventure.getAddress().getId(),
				newFishingAdventure.getAddress().getStreet(), newFishingAdventure.getAddress().getCity(),
				newFishingAdventure.getAddress().getState(), newFishingAdventure.getAddress().getLongitude(),
				newFishingAdventure.getAddress().getLatitude(), newFishingAdventure.getAddress().getPostalCode());
		address = this.addressService.save(address);
		System.out.print(address.getCity());
		FishingAdventure fishingAdventure = new FishingAdventure(Long.valueOf(0), newFishingAdventure.getName(),
				address, newFishingAdventure.getPromoDescription(), newFishingAdventure.getCapacity(),
				newFishingAdventure.getFishingEquipment(), new HashSet<>(), newFishingAdventure.getBehaviourRules(),
				newFishingAdventure.getPricePerHour(), new HashSet<>(), newFishingAdventure.isCancellationFree(),
				newFishingAdventure.getCancellationFee(), new HashSet<>());
		fishingAdventure.setDeleted(false);
		fishingAdventure.setGrade(0);
		fishingAdventure.setNumberOfReviews(0);
		MyUser instructor = this.myUserService.findUserById(newFishingAdventure.getInstructorId());
		fishingAdventure.setInstructor(instructor);
		fishingAdventure = this.save(fishingAdventure);
		additionalServicesService.addMultipleFishingAdventureServices(fishingAdventure,
				newFishingAdventure.getAdditionalServices());
		return fishingAdventure.getId();
	}

	public boolean edit(FishingAdventureDTO fishingAdventureDTO) {
		FishingAdventure fishingAdventure = this.getFishingAdventureById(fishingAdventureDTO.getId());
		List<AdditionalServices> additionalServices = this.additionalServicesService
				.getAllByFishingAdventureId(fishingAdventureDTO.getId());

		if (!this.fishingAdventureReservationService.canAdventureBeChanged(fishingAdventure.getId())) {
			return false;
		}

		for (AdditionalServices a : additionalServices) {
			for (AdditionalServicesDTO additionalServicesDTO : fishingAdventureDTO.getAdditionalServices()) {
				if (a.getId().equals(additionalServicesDTO.getId())) {
					a.setName(additionalServicesDTO.getName());
					a.setPrice(additionalServicesDTO.getPrice());
					this.additionalServicesService.save(a);
				}
			}
		}

		Address address = this.addressService.getAddressById(fishingAdventureDTO.getAddress().getId());
		address.setId(fishingAdventureDTO.getAddress().getId());
		address.setStreet(fishingAdventureDTO.getAddress().getStreet());
		address.setCity(fishingAdventureDTO.getAddress().getCity());
		address.setState(fishingAdventureDTO.getAddress().getState());
		address.setLongitude(fishingAdventureDTO.getAddress().getLongitude());
		address.setLatitude(fishingAdventureDTO.getAddress().getLatitude());
		address.setPostalCode(fishingAdventureDTO.getAddress().getPostalCode());
		this.addressService.save(address);

		fishingAdventure.setAddress(address);
		fishingAdventure.setName(fishingAdventureDTO.getName());
		fishingAdventure.setPromoDescription(fishingAdventureDTO.getPromoDescription());
		fishingAdventure.setBehaviourRules(fishingAdventureDTO.getBehaviourRules());
		fishingAdventure.setPricePerHour(fishingAdventureDTO.getPricePerHour());
		fishingAdventure.setCancalletionFee(fishingAdventureDTO.getCancellationFee());
		fishingAdventure.setCancalletionFree(fishingAdventureDTO.isCancellationFree());

		this.save(fishingAdventure);

		return true;
	}

	public boolean deleteAdventure(Long id) {

		if (!this.fishingAdventureReservationService.canAdventureBeChanged(id)) {
			return false;
		}
		this.delete(id);
		return true;
	}

	public void saveAdditionalService(AdditionalServicesDTO dto) {
		AdditionalServices additionalServices = new AdditionalServices(dto.getId(), dto.getName(), dto.getPrice(),
				new HashSet<>(), new HashSet<>(), new HashSet<>());
		FishingAdventure adventure = this.getFishingAdventureById(dto.getAdventureId());

		Set<FishingAdventure> adventures = additionalServices.getFishingAdventures();
		adventures.add(adventure);
		additionalServices.setFishingAdventures(adventures);
		this.additionalServicesService.save(additionalServices);
	}

	public boolean deleteAdditionalService(@PathVariable("id") Long id, @PathVariable("adventureId") Long adventureId) {
		List<AdventureReservation> allReservations = this.fishingAdventureReservationService
				.getAllByFishingAdventure_Id(adventureId);

		for (AdventureReservation reservation : allReservations) {
			if (!reservation.isAvailabilityPeriod() && !reservation.getCancelled() && !reservation.isAvailable()) {
				Long endDate = reservation.getEndDate().getTime();
				Long today = new Date().getTime();

				if (endDate < today) {
					continue;
				}
				for (AdditionalServices service : reservation.getAdditionalServices()) {
					if (service.getId().equals(id)) {
						return false;
					}
				}
			}
		}
		AdditionalServices service = this.additionalServicesService.getAdditionalServicesById(id);
		service.setFishingAdventures(null);
		service.setAdventureReservationsServices(null);
		this.additionalServicesService.save(service);
		this.additionalServicesService.deleteById(id);
		return true;
	}

	public boolean deleteAllAdventuresByInstructor(Long id) {
		boolean deletedAll = true;
		List<FishingAdventure> allAdventures = this.getFishingAdventuresByInstructor(id);
		for (FishingAdventure a : allAdventures) {
			deletedAll = this.deleteAdventure(a.getId());
			if (!deletedAll) {
				return deletedAll;
			}
		}
		return deletedAll;
	}
}
