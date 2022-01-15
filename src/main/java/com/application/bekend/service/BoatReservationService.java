package com.application.bekend.service;

import com.application.bekend.DTO.BoatReservationDTO;
import com.application.bekend.model.BoatReservation;
import com.application.bekend.model.HouseReservation;
import com.application.bekend.model.MyUser;
import com.application.bekend.repository.BoatRepository;
import com.application.bekend.repository.BoatReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BoatReservationService {

    private final BoatReservationRepository boatReservationRepository;

    @Autowired
    public BoatReservationService(BoatReservationRepository boatReservationRepository) {
        this.boatReservationRepository = boatReservationRepository;
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
        int daysDifference = this.getDaysDifference(boatReservation.getStartDate(), boatReservation.getEndDate());
        double totalPrice = daysDifference*boatReservation.getPrice();
        return totalPrice;
    }

    private int getDaysDifference(Date startData, Date endDate){
        long date1InMs = startData.getTime();
        long date2InMs = endDate.getTime();

        long timeDiff = 0;
        if(date1InMs > date2InMs) {
            timeDiff = date1InMs - date2InMs;
        } else {
            timeDiff = date2InMs - date1InMs;
        }

        // converting diff into days
        int daysDiff = (int) (timeDiff / (1000 * 60 * 60* 24));

        return daysDiff;
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
}
