package com.application.bekend.service;

import com.application.bekend.DTO.BoatReservationDTO;
import com.application.bekend.DTO.ReservationCheckDTO;
import com.application.bekend.model.AdditionalServices;
import com.application.bekend.model.BoatReservation;
import com.application.bekend.model.MyUser;
import com.application.bekend.repository.BoatReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class BoatReservationService {

    private final BoatReservationRepository boatReservationRepository;
    private final AdditionalServicesService additionalServicesService;

    @Autowired
    public BoatReservationService(BoatReservationRepository boatReservationRepository, AdditionalServicesService additionalServicesService) {
        this.boatReservationRepository = boatReservationRepository;
        this.additionalServicesService = additionalServicesService;
    }

    public List<BoatReservation> getAllByBoat_Id(Long id) {return boatReservationRepository.getAllByBoat_Id(id); }

    public BoatReservation save(BoatReservation boatReservation) {
        return this.boatReservationRepository.save(boatReservation);
    }

    public BoatReservation getBoatReservationById(Long id) {
        return boatReservationRepository.getBoatReservationById(id);
    }

    public void delete(Long id){
        boatReservationRepository.deleteById(id);
    }

    public List<BoatReservation> getBoatReservationsByGuestId(Long id) { return this.boatReservationRepository.getBoatReservationsByGuestId(id); }

    public List<BoatReservation> getBoatReservationByBoatOwnerId(Long id) { return this.boatReservationRepository.getBoatReservationByBoatOwnerId(id); }

    public double findTotalPriceForBoatReservation(BoatReservation boatReservation){
        Set<AdditionalServices> additionalServices =  this.additionalServicesService.getAllByBoatReservationId(boatReservation.getId());
        double totalPrice = boatReservation.getPrice();
        for(AdditionalServices a: additionalServices){
            totalPrice = totalPrice + a.getPrice();
        }
        return totalPrice;
    }

    public void canBeCancelled(BoatReservationDTO dto, BoatReservation b){

        Date currentDate = new Date();
        long startDateTime = b.getStartDate().getTime();
        long currentMilis = currentDate.getTime();
        long endDateTime = b.getEndDate().getTime();
        if(currentMilis < endDateTime) {
            if(currentMilis >= startDateTime && currentMilis <=endDateTime){
                dto.setOnGoing(true);
            }else{
                long timeDiff = startDateTime -  currentMilis;
                float daysDiff = timeDiff / (1000 * 60 * 60* 24);
                if (daysDiff >= 3){
                    dto.setCanBeCancelled(true);
                }
            }
        }
    }

    public void cancelReservation(Long id) {
        BoatReservation boatReservation = this.getBoatReservationById(id);
        boatReservation.setCancelled(true);
        MyUser user =  boatReservation.getGuest();
    }


    public boolean findBoatAvailability(ReservationCheckDTO reservationCheckDTO, Long id) {
        List<BoatReservation> boatReservations = this.getAllByBoat_Id(id);
        boolean isAvailable = true;
        for(BoatReservation b: boatReservations){
            Long startDateMilis = b.getStartDate().getTime();
            Long endDateMilis = b.getEndDate().getTime();
            isAvailable = isAvailable(reservationCheckDTO, isAvailable, b, startDateMilis, endDateMilis);
            if(!isAvailable){
                break;
            }
        }
        return isAvailable;
    }

    private boolean isAvailable(ReservationCheckDTO reservationCheckDTO, boolean isAvailable, BoatReservation b, Long startDateMilis, Long endDateMilis) {
        isAvailable = false;
        if(!(b.isAction()&& b.isAvailable() ==true) || b.getCancelled() == true){
            if(!(reservationCheckDTO.getStartMilis() <= startDateMilis && reservationCheckDTO.getEndMilis() >= endDateMilis)){
                if(!checkIsBetween(reservationCheckDTO, startDateMilis, endDateMilis)){
                    isAvailable = true;
                }
            }
        }else{
            isAvailable = isAvailableAction(reservationCheckDTO, b, startDateMilis, endDateMilis);

        }

        return isAvailable;
    }

    private boolean isAvailableAction(ReservationCheckDTO reservationCheckDTO, BoatReservation b, Long startDateMilis, Long endDateMilis) {
        boolean isAvailable = true;
        if(b.isAction() && b.isAvailable() == true){
            if(checkIsBetween(reservationCheckDTO, startDateMilis, endDateMilis)){
                isAvailable = false;
            }
        }
        return isAvailable;
    }

    private boolean checkIsBetween(ReservationCheckDTO reservationCheckDTO, Long startDateMilis, Long endDateMilis) {
        boolean isBetween = false;
        if(IsTimeBetween(reservationCheckDTO.getStartMilis(), startDateMilis, endDateMilis) || IsTimeBetween(reservationCheckDTO.getEndMilis(), startDateMilis, endDateMilis) ){
            isBetween = true;
        }

        return isBetween;
    }

    private boolean IsTimeBetween(Long reservationMilis, Long startDateMilis, Long endDataMilis) {
        return reservationMilis >= startDateMilis && reservationMilis <= endDataMilis;
    }

	public List<BoatReservation> findAll() {
		// TODO Auto-generated method stub
		return this.boatReservationRepository.findAll();
	}
}
