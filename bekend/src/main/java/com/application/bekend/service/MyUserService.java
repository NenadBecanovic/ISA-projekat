package com.application.bekend.service;

import com.application.bekend.DTO.*;
import com.application.bekend.model.*;
import com.application.bekend.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    private MyUserRepository myUserRepository;

    private final AddresService addresService;
    private final RequestForAccountDeletingService requestForAccountDeletingService;
    private final SubscriptionService subscriptionService;
    private final HouseReservationService houseReservationService;
    private final BoatReservationService boatReservationService;
    private final EmailService emailService;
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }       // za enkodovanje lozinke

    @Autowired
    public MyUserService(AddresService addresService, RequestForAccountDeletingService requestForAccountDeletingService, SubscriptionService subscriptionService, HouseReservationService houseReservationService,
                         BoatReservationService boatReservationService, EmailService emailService) {
        this.addresService = addresService;
        this.requestForAccountDeletingService = requestForAccountDeletingService;
        this.subscriptionService = subscriptionService;
        this.houseReservationService = houseReservationService;
        this.boatReservationService = boatReservationService;
        this.emailService = emailService;
    }

    public MyUser findUserById(Long id){
        return myUserRepository.findMyUserById(id);
    }

    public MyUser findUserByEmailorUsername(String email, String username){
        return myUserRepository.findMyUserByEmailOrUsername(email, username);
    }

    public MyUser findUserByEmail(String email){
        return myUserRepository.findMyUserByEmail(email);
    }

    public void activateUser(Long id){
        MyUser user = findUserById(id);
        user.setActivated(true);
        this.myUserRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = this.myUserRepository.findMyUserByUsername(username);
        if (myUser == null){
            throw new UsernameNotFoundException("Username or email not found");
        }
        return myUser;
    }


    public MyUser updateUser(MyUserDTO myUserDTO){
        MyUser myUser = findUserByEmail(myUserDTO.getEmail());
        Address address = this.addresService.updateAddress(myUserDTO.getAddressDTO());
        myUser.setFirstName(myUserDTO.getFirstName());
        myUser.setLastName(myUserDTO.getLastName());
        myUser.setEmail(myUserDTO.getEmail());
        myUser.setUsername(myUserDTO.getUsername());
        if (myUserDTO.isPasswordChange()){
            myUser.setPassword(this.passwordEncoder().encode(myUserDTO.getPassword()));
        }
        myUser.setAddress(address);
        myUser.setPhoneNumber(myUserDTO.getPhoneNumber());
        myUser.setFirstLogin(false);
        MyUser user = this.myUserRepository.save(myUser);
        return user;
    }

    public RequestForAccountDeleting saveDeleteRequest(RequestForAccountDeleting requestForAccountDeleting){
        return this.requestForAccountDeletingService.save(requestForAccountDeleting);
    }

    public Set<MyUser> getAllByHouseId(Long id) { return this.myUserRepository.getAllByHouseId(id); }

    public Set<MyUser> getAllByBoatId(Long id) { return this.myUserRepository.getAllByBoatId(id); }

    public MyUser save(MyUser myUser) {
        return this.myUserRepository.save(myUser);
    }

    public MyUser findUserByHouseReservationId(Long id){
        return myUserRepository.findMyUserByHouseReservationId(id);
    }

    public MyUser findUserByBoatReservationId(Long id){
        return myUserRepository.findUserByBoatReservationId(id);
    }
    
    public MyUser findUserByAdventureReservationId(Long id){
        return myUserRepository.findMyUserByAdventureReservationId(id);
    }

    public Subscription save(Subscription subscription) {
        return this.subscriptionService.save(subscription);
    }

    public MyUser findUserByHouseId(Long id) {
        return myUserRepository.findMyUserByHouseId(id);
    }

    public MyUser findUserByBoatId(Long id) {
        return myUserRepository.findMyUserByBoatId(id);
    }

    public Boolean checkIfUserIsSubscribed(Long userId, Long ownerId){
        return this.subscriptionService.checkIfUserIsSubscribed(userId, ownerId);
    }

    public List<Subscription> findAllBySubscribedUserId(Long id){
        return this.subscriptionService.findAllBySubscribedUserId(id);
    }

    public void deleteSubscriptionById(Long id){
        this.subscriptionService.deleteSubscriptionById(id);
    }
    
    public List<MyUser> getAllUsers() {
    	return this.myUserRepository.findAll();
    }
    
    public boolean deleteUser(Long id) {
    	MyUser user = this.findUserById(id);
    	user.setDeleted(true);
    	this.save(user);
    	return true;
    }
    
    @Transactional
    public boolean deleteUserWithRequest(Long id, String clientMessage) throws MessagingException {
    	RequestForAccountDeleting request = this.requestForAccountDeletingService.findById(id);
    	MyUser user = this.findUserById(request.getUser().getId());
    	user.setDeleted(true);
    	this.save(user);
    	request.setAnswered(true);
    	this.requestForAccountDeletingService.save(request);
    	this.emailService.sendAnswerEmail(new EmailDTO("Odgovor na zahtev za brisanje naloga", clientMessage, user.getEmail()));
    	return true;
    }
    
    @Transactional
    public boolean declineDeleteRequest(Long id, String clientMessage) throws MessagingException {
    	RequestForAccountDeleting request = this.requestForAccountDeletingService.findById(id);
    	request.setAnswered(true);
    	this.requestForAccountDeletingService.save(request);
    	this.emailService.sendAnswerEmail(new EmailDTO("Odgovor na zahtev za brisanje naloga", clientMessage, request.getUser().getEmail()));
    	return true;
    }
    
    public List<RequestForAccountDeleting> getAllDeleteRequests(){
    	return this.requestForAccountDeletingService.getAllRequests();
    }
    
    public List<RequestForAccountDeletingDTO> getAllDeleteRequestsDTO() {
        List<RequestForAccountDeleting> allRequests = this.getAllDeleteRequests();
        List<RequestForAccountDeletingDTO> allRequestsDTO = new ArrayList<RequestForAccountDeletingDTO>();
        for(RequestForAccountDeleting request: allRequests) {
        	UserInfoDTO userDTO = new UserInfoDTO(request.getUser().getId(), request.getUser().getFirstName(), request.getUser().getLastName(), request.getUser().getEmail(), "");
        	allRequestsDTO.add(new RequestForAccountDeletingDTO(request.getId(),request.getDescription(),userDTO));
        }
        return allRequestsDTO;
    }
    
    public List<MyUser> getAllNotActivated(){
    	return this.myUserRepository.findMyUserByIsActivated(false);
    }
    
    public boolean activateNewUser(Long id) throws MessagingException {
    	MyUser newUser = this.myUserRepository.getById(id);
    	newUser.setActivated(true);
    	this.save(newUser);
    	this.emailService.sendHTMLMail(newUser);
    	return true;
    }
    
    public boolean declineNewUserRequest(Long id, String clientMessage) throws MessagingException {
    	MyUser newUser = this.myUserRepository.getById(id);
    	this.emailService.sendAnswerEmail(new EmailDTO("Odgovor na zahtev za kreiranje naloga", clientMessage, newUser.getEmail()));
    	newUser.setAddress(null);
    	this.myUserRepository.delete(newUser);
    	return true;
    }

    public void sendSubscribedUsersEmail(HouseReservationDTO dto, BoatReservationDTO boatDTO, AdventureReservationDTO adventureDTO, String houseName, String boatName, String adventureName) throws MessagingException {
        List<MyUser> myUsers = new ArrayList<>();

        if (dto != null) {
            MyUser owner = findUserByHouseId(dto.getHouseId());
            myUsers = this.myUserRepository.findSubscribedUsersByOwnerId(owner.getId());
            if (myUsers != null) {
                this.emailService.sendActionMail(myUsers, houseName, "", "");
            }
        } else if (boatDTO != null){
            MyUser owner = findUserByBoatId(boatDTO.getBoatId());
            myUsers = this.myUserRepository.findSubscribedUsersByOwnerId(owner.getId());
            if (myUsers != null) {
                this.emailService.sendActionMail(myUsers, "", "", boatName);
            }
        } else if (adventureDTO != null){
            MyUser instructor = findUserByAdventureId(adventureDTO.getAdventureId());
            myUsers = this.myUserRepository.findSubscribedUsersByOwnerId(instructor.getId());
            if (myUsers != null) {
                this.emailService.sendActionMail(myUsers, "", "", adventureName);
            }
        }
    }

    public void sendMailToClient(HouseReservationDTO dto, BoatReservationDTO boatDTO, AdventureReservationDTO adventureDTO, String houseName, String boatName, String adventureName) throws MessagingException {
        if (dto != null) {
            MyUser guest = findUserById(dto.getGuestId());
            this.emailService.sendMailForClient(guest, houseName, "", "");
        } else if(boatDTO != null){
            MyUser guest = findUserById(boatDTO.getGuestId());
            this.emailService.sendMailForClient(guest, "", boatName, "");
        } else if(adventureDTO != null){
            MyUser guest = findUserById(adventureDTO.getGuestId());
            this.emailService.sendMailForClient(guest, "", "", adventureName);
        }
    }
  
	public void editPersonalDescription(Long id, String personalDescription) {
		MyUser instructor = this.findUserById(id);
		instructor.setPersonalDescription(personalDescription);
		this.save(instructor);
	}

    public List<MyUser> getAllInstructors() {
        return this.myUserRepository.findAllInstructors();
    }

    public MyUser findUserByAdventureId(Long id) {
        return myUserRepository.findMyUserByAdventureId(id);
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void lowerPenaltyScoreForUsers(){

        for(MyUser myUser: this.getAllUsers()){
            if(myUser.getPenalties() > 0){
                myUser.setPenalties(myUser.getPenalties() -1);
                this.save(myUser);
            }
        }
    }
}
