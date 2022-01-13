package com.application.bekend.controller;


import com.application.bekend.DTO.*;
import com.application.bekend.model.RequestForAccountDeleting;
import com.application.bekend.model.Subscription;
import com.application.bekend.service.AppealService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.application.bekend.model.Appeal;
import com.application.bekend.model.MyUser;
import com.application.bekend.service.HouseService;
import com.application.bekend.service.MyUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

@RestController
@RequestMapping("api/user")
public class MyUserController {

    private final MyUserService myUserService;
    private final ModelMapper modelMapper;
    private final HouseService houseService;
    private final AppealService appealService;

    @Autowired
    public MyUserController(MyUserService myUserService, ModelMapper modelMapper, HouseService houseService, AppealService appealService) {
        this.myUserService = myUserService;
        this.modelMapper = modelMapper;
        this.houseService = houseService;
        this.appealService = appealService;
    }

    @GetMapping("/findUserByEmail/{email}")
    public ResponseEntity<MyUserDTO> findUserByEmail(@PathVariable("email") String email){
        MyUser myUser = this.myUserService.findUserByEmail(email);
        MyUserDTO dto = modelMapper.map(myUser, MyUserDTO.class);
        AddressDTO addressDTO = modelMapper.map(myUser.getAddress(), AddressDTO.class);

        dto.setAuthority(myUser.getAuthorities().get(0).getName());
        dto.setAddressDTO(addressDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<MyUserDTO> updateUser(@RequestBody MyUserDTO myUserDTO){
        MyUser myUser = this.myUserService.updateUser(myUserDTO);
        MyUserDTO dto = modelMapper.map(myUser, MyUserDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/saveDeleteRequest")
    public ResponseEntity<RequestForAccountDeletingDTO> updateUser(@RequestBody RequestForAccountDeletingDTO dto){
        MyUser user = this.myUserService.findUserByEmail(dto.getUserInfo().getEmail());
        RequestForAccountDeleting requestForAccountDeleting = new RequestForAccountDeleting();
        requestForAccountDeleting.setUser(user);
        requestForAccountDeleting.setDescription(dto.getDescription());
        this.myUserService.saveDeleteRequest(requestForAccountDeleting);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/getAllByHouseId/{id}")
    public ResponseEntity<Set<MyUserDTO>> getAllByHouseId(@PathVariable("id") Long id) {
        Set<MyUserDTO> myUserDTOS = new HashSet<>();

        for (MyUser user: this.myUserService.getAllByHouseId(id)) {
            AddressDTO addressDTO = new AddressDTO(user.getAddress().getId(), user.getAddress().getStreet(), user.getAddress().getCity(), user.getAddress().getState(),
                    user.getAddress().getLongitude(), user.getAddress().getLatitude(), user.getAddress().getPostalCode());

            MyUserDTO dto = new MyUserDTO(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getUsername(), addressDTO, user.getPhoneNumber());
            dto.setId(user.getId());
            myUserDTOS.add(dto);
        }

        return new ResponseEntity<>(myUserDTOS, HttpStatus.OK);
    }

    @GetMapping("/getAllByBoatId/{id}")
    public ResponseEntity<Set<MyUserDTO>> getAllByBoatId(@PathVariable("id") Long id) {
        Set<MyUserDTO> myUserDTOS = new HashSet<>();

        for (MyUser user: this.myUserService.getAllByBoatId(id)) {
            AddressDTO addressDTO = new AddressDTO(user.getAddress().getId(), user.getAddress().getStreet(), user.getAddress().getCity(), user.getAddress().getState(),
                    user.getAddress().getLongitude(), user.getAddress().getLatitude(), user.getAddress().getPostalCode());

            MyUserDTO dto = new MyUserDTO(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getUsername(), addressDTO, user.getPhoneNumber());
            dto.setId(user.getId());
            myUserDTOS.add(dto);
        }

        return new ResponseEntity<>(myUserDTOS, HttpStatus.OK);
    }

    @GetMapping("/findUserByHouseReservationId/{id}")
    public ResponseEntity<MyUserDTO> findUserByHouseReservationId(@PathVariable("id") Long id) {
        MyUser myUser = this.myUserService.findUserByHouseReservationId(id);

        AddressDTO addressDTO = new AddressDTO(myUser.getAddress().getId(), myUser.getAddress().getStreet(), myUser.getAddress().getCity(), myUser.getAddress().getState(),
                myUser.getAddress().getLongitude(), myUser.getAddress().getLatitude(), myUser.getAddress().getPostalCode());
        MyUserDTO dto = new MyUserDTO(myUser.getId(), myUser.getFirstName(), myUser.getLastName(), myUser.getEmail(), myUser.getPassword(), myUser.getUsername(),
                addressDTO, myUser.getPhoneNumber());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/findUserByBoatReservationId/{id}")
    public ResponseEntity<MyUserDTO> findUserByBoatReservationId(@PathVariable("id") Long id) {
        MyUser myUser = this.myUserService.findUserByBoatReservationId(id);

        AddressDTO addressDTO = new AddressDTO(myUser.getAddress().getId(), myUser.getAddress().getStreet(), myUser.getAddress().getCity(), myUser.getAddress().getState(),
                myUser.getAddress().getLongitude(), myUser.getAddress().getLatitude(), myUser.getAddress().getPostalCode());
        MyUserDTO dto = new MyUserDTO(myUser.getId(), myUser.getFirstName(), myUser.getLastName(), myUser.getEmail(), myUser.getPassword(), myUser.getUsername(),
                addressDTO, myUser.getPhoneNumber());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/saveSubscription")
    public ResponseEntity<SubscriptionDTO> updateUser(@RequestBody SubscriptionDTO dto){
        MyUser user = modelMapper.map(dto.getSubscribedUser(), MyUser.class);
        MyUser owner = modelMapper.map(dto.getOwner(), MyUser.class);
        if(user.getEmail() == owner.getEmail()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Subscription subscription = new Subscription();
        subscription.setSubscribedUser(user);
        subscription.setOwner(owner);
        this.myUserService.save(subscription);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/findUserByHouseId/{id}")
    public ResponseEntity<MyUserDTO> findUserByHouseId(@PathVariable("id") Long id){
        MyUser myUser = this.myUserService.findUserByHouseId(id);
        MyUserDTO dto =  modelMapper.map(myUser, MyUserDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/checkIfUserIsSubscribed/{userId}/{ownerId}")
    public ResponseEntity<Boolean> checkIfUserIsSubscribed(@PathVariable("userId") Long userId, @PathVariable("ownerId") Long ownerId){
        Boolean isSubscribed = this.myUserService.checkIfUserIsSubscribed(userId, ownerId);

        return new ResponseEntity<>(isSubscribed, HttpStatus.OK);
    }

    @GetMapping("/findUserByBoatId/{id}")
    public ResponseEntity<MyUserDTO> findUserByBoatId(@PathVariable("id") Long id){
        MyUser myUser = this.myUserService.findUserByBoatId(id);
        MyUserDTO dto =  modelMapper.map(myUser, MyUserDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/findAllSubscriptionsByUserId/{id}")
    public ResponseEntity<List<SubscriptionDTO>> findAllSubscriptionsByUserId(@PathVariable("id") Long id){
        List<Subscription> subscriptions = this.myUserService.findAllBySubscribedUserId(id);
        List<SubscriptionDTO> subscriptionDTOS = new ArrayList<>();
        for(Subscription subscription: subscriptions){
            SubscriptionDTO dto = modelMapper.map(subscription, SubscriptionDTO.class);
            subscriptionDTOS.add(dto);
        }

        return new ResponseEntity<>(subscriptionDTOS, HttpStatus.OK);
    }

    @DeleteMapping("/deleteSubscriptionById/{id}")
    public ResponseEntity<Boolean> deleteSubscriptionById(@PathVariable("id") Long id){
        this.myUserService.deleteSubscriptionById(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/saveAppeal")
    public ResponseEntity<AppealDTO> saveAppealEntity(@RequestBody AppealDTO dto){

        if(dto.isHasHouse()){
            this.appealService.saveAppealHouse(dto);
        }else if(dto.isHasHouseOwner()){
            this.appealService.saveAppealHouseOwner(dto);
        }else if(dto.isHasBoat()){
            this.appealService.saveAppealBoat(dto);
        }else if(dto.isHasBoatOwner()){
            this.appealService.saveAppealBoatOwner(dto);
        }else{
            this.appealService.saveAppealInstructor(dto);
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    @GetMapping("/findUserByFishingAdventureReservationId/{id}")
    public ResponseEntity<UserInfoDTO> findUserByFishingAdventureReservationId(@PathVariable("id") Long id) {
        MyUser myUser = this.myUserService.findUserById(id);

        UserInfoDTO adventureUserDTO = new UserInfoDTO(myUser.getId(), myUser.getFirstName(), myUser.getLastName(), myUser.getEmail(), myUser.getAuthorities().get(0).getName());

        return new ResponseEntity<>(adventureUserDTO, HttpStatus.OK);
    }
    
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserInfoDTO>> getAllUsers() {
        List<MyUser> allUsers = this.myUserService.getAllUsers();
        List<UserInfoDTO> allUsersDTO = new ArrayList<UserInfoDTO>();
        for(MyUser myUser: allUsers) {
        	UserInfoDTO userDTO = new UserInfoDTO(myUser.getId(), myUser.getFirstName(), myUser.getLastName(), myUser.getEmail(), "");
        	allUsersDTO.add(userDTO);
        }
        return new ResponseEntity<>(allUsersDTO, HttpStatus.OK);
    }
    
    @PutMapping("/delete")
    public ResponseEntity<Boolean> deleteUser(@RequestBody Long id) {
    	boolean isDeleted = this.myUserService.deleteUser(id);
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }
    
    @PutMapping("/deleteUserWithRequest/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id, @RequestBody AdminAnswerDTO adminAnswer) throws MessagingException{
    	boolean isDeleted = this.myUserService.deleteUserWithRequest(id, adminAnswer.getClientResponse());
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }
    
    @PutMapping("/declineDeleteRequest/{id}")
    public ResponseEntity<Boolean> declineDeleteRequest(@PathVariable("id") Long id, @RequestBody AdminAnswerDTO adminAnswer) throws MessagingException{
    	boolean isDeleted = this.myUserService.declineDeleteRequest(id, adminAnswer.getClientResponse());
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }
    
    @GetMapping("/getAllDeleteRequests")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<RequestForAccountDeletingDTO>> getAllDeleteRequests() {
        List<RequestForAccountDeleting> allRequests = this.myUserService.getAllDeleteRequests();
        List<RequestForAccountDeletingDTO> allRequestsDTO = new ArrayList<RequestForAccountDeletingDTO>();
        for(RequestForAccountDeleting request: allRequests) {
        	UserInfoDTO userDTO = new UserInfoDTO(request.getUser().getId(), request.getUser().getFirstName(), request.getUser().getLastName(), request.getUser().getEmail(), "");
        	allRequestsDTO.add(new RequestForAccountDeletingDTO(request.getId(),request.getDescription(),userDTO));
        }
        return new ResponseEntity<>(allRequestsDTO, HttpStatus.OK);
    }
    
    @GetMapping("/getAllAppeals")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AppealDTO>> getAllAppeals() {
        List<Appeal> allAppeals = this.appealService.getAllAppeals();
        List<AppealDTO> allAppealsDTO = new ArrayList<AppealDTO>();
        for(Appeal appeal: allAppeals) {
        	UserInfoDTO guest = new UserInfoDTO(appeal.getSenderId().getId(), appeal.getSenderId().getFirstName(), appeal.getSenderId().getLastName(), appeal.getSenderId().getEmail(), "");
        	UserInfoDTO owner = new UserInfoDTO(appeal.getOwnerId().getId(), appeal.getOwnerId().getFirstName(), appeal.getOwnerId().getLastName(), appeal.getOwnerId().getEmail(), "");
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
    public ResponseEntity<Boolean> sendAppealResponse(@PathVariable("id") Long id, @RequestBody ReportAppealAnswerDTO answerDTO) throws MessagingException{
    	boolean isAnswered = this.appealService.sendAppealResponse(id, answerDTO);
        return new ResponseEntity<>(isAnswered, HttpStatus.OK);
    }
    
    @GetMapping("/getAllNewUserRequests")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<NewUserRequestDTO>> getAllNewUserRequests(){
    	List<MyUser> allUserRequests = this.myUserService.getAllNotActivated();
    	List<NewUserRequestDTO> allRequestsDTO = new ArrayList<NewUserRequestDTO>();
    	for(MyUser user: allUserRequests) {
    		allRequestsDTO.add(new NewUserRequestDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getReasonForRegistration()));
    	}
    	return new ResponseEntity<>(allRequestsDTO, HttpStatus.OK);
    }
    
    @PutMapping("/activateNewUser")
    public ResponseEntity<Boolean> sendAppealResponse(@RequestBody Long id) throws MessagingException{
    	boolean isAnswered = this.myUserService.activateNewUser(id);
        return new ResponseEntity<>(isAnswered, HttpStatus.OK);
    }
    
    @PutMapping("/declineNewUserRequest/{id}")
    public ResponseEntity<Boolean> declineNewUserRequest(@PathVariable("id") Long id, @RequestBody AdminAnswerDTO adminAnswer) throws MessagingException{
    	boolean isDeleted = this.myUserService.declineNewUserRequest(id, adminAnswer.getClientResponse());
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }
    
    @PutMapping("/editInstructorPersonalDescription/{id}")
    public ResponseEntity updateUser(@PathVariable("id") Long id,@RequestBody String personalDescription){
        this.myUserService.editPersonalDescription(id, personalDescription);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
