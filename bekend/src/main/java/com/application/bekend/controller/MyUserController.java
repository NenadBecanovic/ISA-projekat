package com.application.bekend.controller;


import com.application.bekend.DTO.*;
import com.application.bekend.model.*;
import com.application.bekend.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

@RestController
@RequestMapping("api/user")
@Transactional
public class MyUserController {

    private final MyUserService myUserService;
    private final ModelMapper modelMapper;
    private final CancelReservationService cancelReservationService;
    private final FishingInstructorService fishingInstructorService;
    private final HouseReservationService houseReservationService;
    private final BoatReservationService boatReservationService;
    private final FishingAdventureReservationService fishingAdventureRservationService;
    private final RequestForAccountDeletingService requestForAccountDeletingService;

    @Autowired
    public MyUserController(MyUserService myUserService, ModelMapper modelMapper, CancelReservationService cancelReservationService, FishingInstructorService fishingInstructorService,HouseReservationService houseReservationService,
            BoatReservationService boatReservationService, FishingAdventureReservationService fishingAdventureReservationService,
            RequestForAccountDeletingService requestForAccountDeletingService) {
        this.myUserService = myUserService;
        this.modelMapper = modelMapper;
        this.cancelReservationService = cancelReservationService;
        this.fishingInstructorService = fishingInstructorService;
        this.houseReservationService = houseReservationService;
        this.boatReservationService = boatReservationService;
        this.fishingAdventureRservationService = fishingAdventureReservationService;
        this.requestForAccountDeletingService = requestForAccountDeletingService;
    }

    @GetMapping("/findUserByEmail/{email}")
    public ResponseEntity<MyUserDTO> findUserByEmail(@PathVariable("email") String email){
        MyUser myUser = this.myUserService.findUserByEmail(email);
        MyUserDTO dto = modelMapper.map(myUser, MyUserDTO.class);
        dto.setIsFirstLogin(myUser.isFirstLogin());
        AddressDTO addressDTO = modelMapper.map(myUser.getAddress(), AddressDTO.class);

        dto.setAuthority(myUser.getAuthority().getName());
        dto.setAddressDTO(addressDTO);
        if(myUser.getNumberOfReviews() == 0){
            dto.setAvarageGrade(0);
        }else{
            dto.setAvarageGrade(myUser.getGrade()/myUser.getNumberOfReviews());
        }
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
    @PreAuthorize("hasRole('ROLE_HOUSE_OWNER')")
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
            MyUser owner = this.myUserService.findUserById(subscription.getOwner().getId());
            UserInfoDTO ownerInfo = modelMapper.map(owner, UserInfoDTO.class);
            MyUser subcriber = this.myUserService.findUserById(subscription.getSubscribedUser().getId());
            UserInfoDTO subcriberInfo = modelMapper.map(owner, UserInfoDTO.class);
            SubscriptionDTO dto = new SubscriptionDTO();
            dto.setId(subscription.getId());
            dto.setSubscribedUser(subcriberInfo);
            dto.setOwner(ownerInfo);
            subscriptionDTOS.add(dto);
        }

        return new ResponseEntity<>(subscriptionDTOS, HttpStatus.OK);
    }

    @DeleteMapping("/deleteSubscriptionById/{id}")
    @PreAuthorize("hasRole('ROLE_HOUSE_OWNER')")
    public ResponseEntity<Boolean> deleteSubscriptionById(@PathVariable("id") Long id){
        this.myUserService.deleteSubscriptionById(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    
    @GetMapping("/findUserByFishingAdventureReservationId/{id}")
    public ResponseEntity<UserInfoDTO> findUserByFishingAdventureReservationId(@PathVariable("id") Long id) {
        MyUser myUser = this.myUserService.findUserById(id);

        UserInfoDTO adventureUserDTO = new UserInfoDTO(myUser.getId(), myUser.getFirstName(), myUser.getLastName(), myUser.getEmail(), myUser.getAuthority().getName());

        return new ResponseEntity<>(adventureUserDTO, HttpStatus.OK);
    }


    @PostMapping("/cancelReservation")
    @PreAuthorize("hasRole('ROLE_HOUSE_OWNER')")
    public ResponseEntity<ReservationCancelDTO> cancelReservation(@RequestBody ReservationCancelDTO dto){
        this.cancelReservationService.cancelReservation(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserInfoDTO>> getAllUsers() {
        List<MyUser> allUsers = this.myUserService.getAllUsers();
        List<UserInfoDTO> allUsersDTO = new ArrayList<UserInfoDTO>();
        for(MyUser myUser: allUsers) {
        	if(!myUser.isDeleted()) {
	        	UserInfoDTO userDTO = new UserInfoDTO(myUser.getId(), myUser.getFirstName(), myUser.getLastName(), myUser.getEmail(), "");
	        	allUsersDTO.add(userDTO);
        	}
        }
        return new ResponseEntity<>(allUsersDTO, HttpStatus.OK);
    }
    
    @PutMapping("/delete")
    public ResponseEntity<Boolean> deleteUser(@RequestBody Long id) {
    	MyUser user = this.myUserService.findUserById(id);
    	if(!user.getAuthority().getName().equals("ROLE_USER")) {
    		if (!(this.fishingAdventureRservationService.getAdventureReservationsByInstructorId(id).size() == 0)) {
    			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    		}
    		if (!(this.boatReservationService.getBoatReservationByBoatOwnerId(id).size() == 0)) {
    			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    		}
    		if (!(this.houseReservationService.getHouseReservationByHouseOwnerId(id).size() == 0)) {
    			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    		}
    	} else if(user.getAuthority().getName().equals("ROLE_USER")) {
    		if (!(this.fishingAdventureRservationService.getAdventureReservationsByGuestId(id).size() == 0)) {
    			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    		}
    		if (!(this.boatReservationService.getBoatReservationsByGuestId(id).size() == 0)) {
    			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    		}
    		if (!(this.houseReservationService.getHouseReservationsByGuestId(id).size() == 0)) {
    			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    		}
    	}
    	
    	boolean isDeleted = this.myUserService.deleteUser(user);
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }
    
    @PutMapping("/deleteUserWithRequest/{id}")
    public ResponseEntity<Boolean> deleteUserWithRequest(@PathVariable("id") Long id, @RequestBody AdminAnswerDTO adminAnswer) throws MessagingException{
    	RequestForAccountDeleting request = this.requestForAccountDeletingService.findById(id);
    	MyUser user = this.myUserService.findUserById(request.getUser().getId());
    	if(!user.getAuthority().getName().equals("ROLE_USER")) {
    		if (!(this.fishingAdventureRservationService.getAdventureReservationsByInstructorId(id).size() == 0)) {
    			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    		}
    		if (!(this.boatReservationService.getBoatReservationByBoatOwnerId(id).size() == 0)) {
    			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    		}
    		if (!(this.houseReservationService.getHouseReservationByHouseOwnerId(id).size() == 0)) {
    			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    		}
    	} else if(user.getAuthority().getName().equals("ROLE_USER")) {
    		if (!(this.fishingAdventureRservationService.getAdventureReservationsByGuestId(id).size() == 0)) {
    			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    		}
    		if (!(this.boatReservationService.getBoatReservationsByGuestId(id).size() == 0)) {
    			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    		}
    		if (!(this.houseReservationService.getHouseReservationsByGuestId(id).size() == 0)) {
    			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    		}
    	}
    	
    	boolean isDeleted = this.myUserService.deleteUserWithRequest(id, adminAnswer.getClientResponse());
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }
    
    @PutMapping("/declineDeleteRequest/{id}")
    public ResponseEntity<Boolean> declineDeleteRequest(@PathVariable("id") Long id, @RequestBody AdminAnswerDTO adminAnswer) throws MessagingException{
    	boolean isDeleted = this.myUserService.declineDeleteRequest(id, adminAnswer.getClientResponse());
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }
    
    @GetMapping("/getAllDeleteRequests")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<List<RequestForAccountDeletingDTO>> getAllDeleteRequests() {
        List<RequestForAccountDeletingDTO> allRequests = this.myUserService.getAllDeleteRequestsDTO();
        return new ResponseEntity<>(allRequests, HttpStatus.OK);
    }
    
    @GetMapping("/getAllNewUserRequests")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<List<NewUserRequestDTO>> getAllNewUserRequests(){
    	List<MyUser> allUserRequests = this.myUserService.getAllNotActivated();
    	List<NewUserRequestDTO> allRequestsDTO = new ArrayList<NewUserRequestDTO>();
    	for(MyUser user: allUserRequests) {
    		allRequestsDTO.add(new NewUserRequestDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getReasonForRegistration()));
    	}
    	return new ResponseEntity<>(allRequestsDTO, HttpStatus.OK);
    }
    
    @PutMapping("/activateNewUser")
    public ResponseEntity<Boolean> activateUser(@RequestBody Long id) throws MessagingException{
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

    @GetMapping("/getAllInstructors")
    public ResponseEntity<List<UserDTO>> getAllInstructors(){
        List<MyUser> myUsers = this.myUserService.getAllInstructors();
        List<UserDTO> userInfoDTOS = getUserDTOS(myUsers);
        return new ResponseEntity<>(userInfoDTOS, HttpStatus.OK);
    }

    private List<UserDTO> getUserDTOS(List<MyUser> myUsers) {
        List<UserDTO> userInfoDTOS = new ArrayList<>();
        for(MyUser m: myUsers){
            UserDTO userInfoDTO = modelMapper.map(m, UserDTO.class);
            AddressDTO addressDTO = modelMapper.map(m.getAddress(), AddressDTO.class);
            UserCategoryDTO userCategoryDTO = modelMapper.map(m.getCategory(), UserCategoryDTO.class);
            if(m.getNumberOfReviews() == 0){
                userInfoDTO.setAvarageGrade(0);
            }else{
                userInfoDTO.setAvarageGrade(m.getGrade()/m.getNumberOfReviews());
            }
            userInfoDTO.setAddressDTO(addressDTO);
            userInfoDTOS.add(userInfoDTO);
        }
        return userInfoDTOS;
    }

    @GetMapping("/findUserById/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable("id") Long id){
        MyUser myUser = this.myUserService.findUserById(id);
        UserDTO userDTO = modelMapper.map(myUser, UserDTO.class);
        AddressDTO addressDTO = modelMapper.map(myUser.getAddress(), AddressDTO.class);
        userDTO.setAddressDTO(addressDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/findUserByAdventureId/{id}")
    public ResponseEntity<MyUserDTO> findUserByAdventureId(@PathVariable("id") Long id){
        MyUser myUser = this.myUserService.findUserByAdventureId(id);
        MyUserDTO dto =  modelMapper.map(myUser, MyUserDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/getAllAvailableInstructors")
    public ResponseEntity<List<UserDTO>> getAllInstructors(@RequestBody ReservationCheckDTO reservationCheckDTO){
        List<MyUser> myUsers = this.fishingInstructorService.getAllAvailableInstructors(reservationCheckDTO);
        List<UserDTO> userInfoDTOS = getUserDTOS(myUsers);
        return new ResponseEntity<>(userInfoDTOS, HttpStatus.OK);
    }


}
