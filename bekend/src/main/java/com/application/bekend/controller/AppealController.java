package com.application.bekend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.bekend.DTO.AppealDTO;
import com.application.bekend.DTO.ReportAppealAnswerDTO;
import com.application.bekend.DTO.UserInfoDTO;
import com.application.bekend.model.Appeal;
import com.application.bekend.service.AppealService;


@RestController
@RequestMapping("api/appeal")
@Transactional
public class AppealController {

	private final AppealService appealService;
	
	@Autowired
    public AppealController(AppealService appealService) {
        this.appealService = appealService;
    }
	
	@PostMapping("/saveAppeal")
	public ResponseEntity<AppealDTO> saveAppealEntity(@RequestBody AppealDTO dto) {

		if (dto.isHasHouse()) {
			this.appealService.saveAppealHouse(dto);
		} else if (dto.isHasHouseOwner()) {
			this.appealService.saveAppealHouseOwner(dto);
		} else if (dto.isHasBoat()) {
			this.appealService.saveAppealBoat(dto);
		} else if (dto.isHasBoatOwner()) {
			this.appealService.saveAppealBoatOwner(dto);
		} else if (dto.isHasInstructor()) {
			this.appealService.saveAppealInstructor(dto);
		}

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/getAllAppeals")
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public ResponseEntity<List<AppealDTO>> getAllAppeals() {
		List<Appeal> allAppeals = this.appealService.getAllAppeals();
		List<AppealDTO> allAppealsDTO = new ArrayList<AppealDTO>();
		for (Appeal appeal : allAppeals) {
			UserInfoDTO guest = new UserInfoDTO(appeal.getSenderId().getId(), appeal.getSenderId().getFirstName(),
					appeal.getSenderId().getLastName(), appeal.getSenderId().getEmail(), "");
			UserInfoDTO owner = new UserInfoDTO(appeal.getOwnerId().getId(), appeal.getOwnerId().getFirstName(),
					appeal.getOwnerId().getLastName(), appeal.getOwnerId().getEmail(), "");
			AppealDTO appealDTO = new AppealDTO();
			appealDTO.setId(appeal.getId());
			appealDTO.setReview(appeal.getReview());
			appealDTO.setGuest(guest);
			appealDTO.setOwner(owner);
			appealDTO.setIsAnswered(appeal.isAnswered());
			allAppealsDTO.add(appealDTO);
		}
		return new ResponseEntity<>(allAppealsDTO, HttpStatus.OK);
	}

	@PutMapping("/sendAppealResponse/{id}")
	public ResponseEntity<Boolean> sendAppealResponse(@PathVariable("id") Long id,
			@RequestBody ReportAppealAnswerDTO answerDTO) throws MessagingException {
		boolean isAnswered = this.appealService.sendAppealResponse(id, answerDTO);
		return new ResponseEntity<>(isAnswered, HttpStatus.OK);
	}
}
