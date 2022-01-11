package com.application.bekend.service;

import com.application.bekend.DTO.FeedbackDTO;
import com.application.bekend.DTO.MyUserDTO;
import com.application.bekend.model.*;
import com.application.bekend.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    private MyUserRepository myUserRepository;

    private final AddresService addresService;
    private final RequestForAccountDeletingService requestForAccountDeletingService;
    private final SubscriptionService subscriptionService;
    private final HouseReservationService houseReservationService;
    private final FeedbackService feedbackService;
    private final BoatReservationService boatReservationService;

    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }       // za enkodovanje lozinke

    @Autowired
    public MyUserService(AddresService addresService, RequestForAccountDeletingService requestForAccountDeletingService, SubscriptionService subscriptionService, HouseReservationService houseReservationService, FeedbackService feedbackService, BoatReservationService boatReservationService) {
        this.addresService = addresService;
        this.requestForAccountDeletingService = requestForAccountDeletingService;
        this.subscriptionService = subscriptionService;
        this.houseReservationService = houseReservationService;
        this.feedbackService = feedbackService;
        this.boatReservationService = boatReservationService;
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
        return myUserRepository.findUserByAdventureReservationId(id);
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

    public void saveFeedbackHouse(FeedbackDTO feedbackDTO){
        HouseReservation houseReservation = this.houseReservationService.getHouseReservationById(feedbackDTO.getReservationId());
        houseReservation.setHasFeedbackEntity(true);
        this.houseReservationService.save(houseReservation);
        Feedback feedback = this.setFeedback(feedbackDTO);
        feedback.setHouse(houseReservation.getHouse());
        this.feedbackService.save(feedback);
    }

    public void saveFeedbackBoat(FeedbackDTO feedbackDTO){
        BoatReservation boatReservation = this.boatReservationService.getBoatReservationById(feedbackDTO.getReservationId());
        boatReservation.setHasFeedbackEntity(true);
        this.boatReservationService.save(boatReservation);
        Feedback feedback = this.setFeedback(feedbackDTO);
        feedback.setBoat(boatReservation.getBoat());
        this.feedbackService.save(feedback);
    }

    public void saveFeedbackHouseOwner(FeedbackDTO feedbackDTO){
        HouseReservation houseReservation = this.houseReservationService.getHouseReservationById(feedbackDTO.getReservationId());
        houseReservation.setHasFeedbackOwner(true);
        this.houseReservationService.save(houseReservation);
        Feedback feedback = this.setFeedback(feedbackDTO);
        feedback.setMyUser(this.findUserById(feedbackDTO.getOwnerId()));;
        this.feedbackService.save(feedback);
    }

    public void saveFeedbackBoatOwner(FeedbackDTO feedbackDTO){
        BoatReservation boatReservation = this.boatReservationService.getBoatReservationById(feedbackDTO.getReservationId());
        boatReservation.setHasFeedbackOwner(true);
        this.boatReservationService.save(boatReservation);
        Feedback feedback = this.setFeedback(feedbackDTO);
        feedback.setMyUser(this.findUserById(feedbackDTO.getOwnerId()));;
        this.feedbackService.save(feedback);
    }

    public void saveFeedbackInstructor(FeedbackDTO feedbackDTO){
//        AdventureReservation adventureReservation = this.adneture.getFishingAdventureById(feedbackDTO.getReservationId())
//        adventureReservation.setHasFeedbackOwner(true);
//        this.fishingAdventureService.save(adventureReservation);
//        Feedback feedback = this.setFeedback(feedbackDTO);
//        feedback.setMyUser(this.findUserById(feedbackDTO.getOwnerId()));;
//        this.feedbackService.save(feedback);
    }

    private Feedback setFeedback(FeedbackDTO feedbackDTO){
        Feedback feedback = new Feedback();
        feedback.setGrade(feedbackDTO.getGrade());
        feedback.setReview(feedbackDTO.getReview());
        feedback.setApproved(false);
        return feedback;
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
    
    public List<RequestForAccountDeleting> getAllDeleteRequests(){
    	return this.requestForAccountDeletingService.getAllRequests();
    }
}
