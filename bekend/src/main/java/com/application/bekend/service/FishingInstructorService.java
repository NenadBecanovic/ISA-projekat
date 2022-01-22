package com.application.bekend.service;

import com.application.bekend.DTO.ReservationCheckDTO;
import com.application.bekend.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FishingInstructorService {

    private final FishingAdventureReservationService fishingAdventureReservationService;
    private final FishingAdventureService fishingAdventureService;
    private final MyUserService myUserService;
    private final AdditionalServicesService additionalServicesService;

    public FishingInstructorService(FishingAdventureReservationService fishingAdventureReservationService, FishingAdventureService fishingAdventureService, MyUserService myUserService, AdditionalServicesService additionalServicesService) {
        this.fishingAdventureReservationService = fishingAdventureReservationService;
        this.fishingAdventureService = fishingAdventureService;
        this.myUserService = myUserService;
        this.additionalServicesService = additionalServicesService;
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
            if(a.getCancelled()){
                continue;
            }
            isAvailable = isAvailable(reservationCheckDTO, a, startDateMilis, endDateMilis);
            if(!isAvailable){
                break;
            }
        }
        return isAvailable;
    }

    private boolean isAvailable(ReservationCheckDTO reservationCheckDTO, AdventureReservation a, Long startDateMilis, Long endDateMilis) {
        boolean isAvailable = false;
        if(!(a.isAction()&& a.isAvailable())){
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


    public void checkIfActionInside(ReservationCheckDTO reservationCheckDTO, Long adventureId){
        List<AdventureReservation> adventureReservations = this.fishingAdventureReservationService.getAllByFishingAdventure_Id(adventureId);

        for(AdventureReservation h: adventureReservations){
            Long startDateMilis = h.getStartDate().getTime();
            Long endDateMilis = h.getEndDate().getTime();
            if(h.isAction() && reservationCheckDTO.getStartMilis() <= startDateMilis && endDateMilis <= reservationCheckDTO.getEndMilis()){
                deleteAdventureReservation(h.getId());
            }
        }
    }

    public void deleteAdventureReservation(Long id){
        AdventureReservation b = this.fishingAdventureReservationService.getFishingAdventureReservationById(id);
        Set<AdditionalServices> additionalServices =  b.getAdditionalServices();     // ne moramo direktno iz baze dobavljati jer ova lista u sebi ima objekte sa svojim pravim id-jevima
        for(AdditionalServices a: additionalServices){
            a.getHouseReservationsServices().remove(b);  // iz niza rezervacija dodatnih usluga izbacimo ovu rezervaciju koju brisemo - raskinuta u tabeli additional_services_house_reservation (sa vodece strane, jer je kod AdditionalService JoinTable)
            this.additionalServicesService.save(a);
        }
        b.setGuest(null);
        b.setFishingAdventure(null);  // raskinuta veza u tabeli house_reservation_table (sa strane vodece veze u ManyToMany vezi)
        b = this.fishingAdventureReservationService.save(b);
        this.fishingAdventureReservationService.delete(b.getId());
    }

}
