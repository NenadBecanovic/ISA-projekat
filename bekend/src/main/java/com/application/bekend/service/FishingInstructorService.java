package com.application.bekend.service;

import com.application.bekend.DTO.ReservationCheckDTO;
import com.application.bekend.model.AdventureReservation;
import com.application.bekend.model.FishingAdventure;
import com.application.bekend.model.MyUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FishingInstructorService {

    private final FishingAdventureReservationService fishingAdventureReservationService;
    private final FishingAdventureService fishingAdventureService;
    private final MyUserService myUserService;

    public FishingInstructorService(FishingAdventureReservationService fishingAdventureReservationService, FishingAdventureService fishingAdventureService, MyUserService myUserService) {
        this.fishingAdventureReservationService = fishingAdventureReservationService;
        this.fishingAdventureService = fishingAdventureService;
        this.myUserService = myUserService;
    }

    public List<MyUser> getAllAvailableInstructors(ReservationCheckDTO reservationCheckDTO) {
        List<MyUser> instructors = this.myUserService.getAllInstructors();
        List<MyUser> availableInstructors = new ArrayList<>();
        for (MyUser i: instructors){

            if(this.findInstructorAvailability(reservationCheckDTO,i.getId())){
                availableInstructors.add(i);
            }
        }
        return availableInstructors;
    }

    public boolean findInstructorAvailability(ReservationCheckDTO reservationCheckDTO, Long id) {
        List<FishingAdventure> allAdventures = this.fishingAdventureService.getFishingAdventuresByInstructor(id);
        boolean isAvailable = false;
        boolean hasCapacity = false;
        for (FishingAdventure f: allAdventures){
            List<AdventureReservation> allReservations = this.fishingAdventureReservationService.getAllByFishingAdventure_Id(f.getId());
            isAvailable = this.findAdventureAvailability(reservationCheckDTO,allReservations);
            if(!isAvailable){
                break;
            }
            hasCapacity = isHasCapacity(reservationCheckDTO, hasCapacity, f);
        }

        return isAvailable && hasCapacity;
    }

    private boolean isHasCapacity(ReservationCheckDTO reservationCheckDTO, boolean hasCapacity, FishingAdventure f) {
        if(f.getCapacity()>= reservationCheckDTO.getMaxGuest()){
            hasCapacity = true;
        }
        return hasCapacity;
    }

    public boolean findAdventureAvailability(ReservationCheckDTO reservationCheckDTO, List<AdventureReservation> allReservations) {
        boolean isAvailable = true;
        for(AdventureReservation a: allReservations){
            Long startDateMilis = a.getStartDate().getTime();
            Long endDateMilis = a.getEndDate().getTime();
            isAvailable = isAvailable(reservationCheckDTO, a, startDateMilis, endDateMilis);
            if(!isAvailable){
                break;
            }
        }
        return isAvailable;
    }

    private boolean isAvailable(ReservationCheckDTO reservationCheckDTO, AdventureReservation a, Long startDateMilis, Long endDateMilis) {
        boolean isAvailable = false;
        if(!(a.isAction()&& a.isAvailable()) || a.getCancelled()){
            if(!(reservationCheckDTO.getStartMilis() <= startDateMilis && reservationCheckDTO.getEndMilis() >= endDateMilis)){
                if(!checkIsBetween(reservationCheckDTO, startDateMilis, endDateMilis)){
                    isAvailable = true;
                }
            }
        }else{
            isAvailable = isAvailableAction(reservationCheckDTO, a, startDateMilis, endDateMilis);
        }

        return isAvailable;
    }

    private boolean isAvailableAction(ReservationCheckDTO reservationCheckDTO, AdventureReservation a, Long startDateMilis, Long endDateMilis) {
        boolean isAvailable = true;
        if(a.isAction() && a.isAvailable()){
            if(checkIsBetween(reservationCheckDTO, startDateMilis, endDateMilis)){
                isAvailable = false;
            }
        }
        return isAvailable;
    }

    private boolean checkIsBetween(ReservationCheckDTO reservationCheckDTO, Long startDateMilis, Long endDateMilis) {
        return IsTimeBetween(reservationCheckDTO.getStartMilis(), startDateMilis, endDateMilis) || IsTimeBetween(reservationCheckDTO.getEndMilis(), startDateMilis, endDateMilis);
    }

    private boolean IsTimeBetween(Long reservationMilis, Long startDateMilis, Long endDataMilis) {
        return reservationMilis >= startDateMilis && reservationMilis <= endDataMilis;
    }

}
