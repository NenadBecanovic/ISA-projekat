package com.application.bekend.service;

import com.application.bekend.DTO.HouseReservationDTO;
import com.application.bekend.DTO.ReservationCheckDTO;
import com.application.bekend.model.House;
import com.application.bekend.model.HouseReservation;
import com.application.bekend.model.Room;
import com.application.bekend.repository.HouseReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HouseReservationService {

    private final HouseReservationRepository houseReservationsRepository;

    @Autowired
    public HouseReservationService(HouseReservationRepository houseReservationsRepository) {
        this.houseReservationsRepository = houseReservationsRepository;
    }

    public List<HouseReservation> getAllByHouse_Id(Long id) {
        return houseReservationsRepository.getAllByHouse_Id(id);
    }

    public HouseReservation save(HouseReservation houseReservation) {
        return this.houseReservationsRepository.save(houseReservation);
    }

    public HouseReservation getHouseReservationById(Long id) {
        return houseReservationsRepository.getHouseReservationById(id);
    }

    public void delete(Long id){
        houseReservationsRepository.deleteById(id);
    }

    public List<HouseReservation> getHouseReservationByUserId(Long id) {
        return houseReservationsRepository.findHouseReservationByGuestId(id);
    }

    public  List<HouseReservation> getHouseReservationsByGuestId(Long id) { return this.houseReservationsRepository.getHouseReservationsByGuestId(id); }

    public double findTotalPriceForHouseReservation(HouseReservation houseReservation){
        int daysDifference = this.getDaysDifference(houseReservation.getStartDate(), houseReservation.getEndDate());
        double totalPrice = daysDifference*houseReservation.getPrice();
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

    public HouseReservationDTO editHouseReservation(HouseReservationDTO houseReservationDTO, Long houseResId){
        HouseReservation houseReservation= this.houseReservationsRepository.getHouseReservationById(houseResId);
        houseReservation.setHasAppealEntity(houseReservationDTO.isHasAppealEntity());
        houseReservation.setHasAppealOwner(houseReservationDTO.isHasAppealOwner());
        houseReservation.setHasFeedbackEntity(houseReservationDTO.isHasFeedbackEntity());
        houseReservation.setHasFeedbackOwner(houseReservationDTO.isHasFeedbackOwner());
        HouseReservation houseReservationEdit = this.houseReservationsRepository.save(houseReservation);

        return houseReservationDTO;
    }

    public void canBeCancelled(HouseReservationDTO dto, HouseReservation b){

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

    public boolean checkMaxGuest(int maxGuest, House h) {
        int maxGuestHouse = 0;
        for(Room r: h.getRooms()){
            maxGuestHouse = maxGuestHouse + r.getNumberOfBeds();
        }

        return maxGuestHouse <= maxGuest;
    }

    public boolean findHouseAvailability(ReservationCheckDTO reservationCheckDTO, Long houseId) {
        List<HouseReservation> houseReservations = this.getAllByHouse_Id(houseId);
        boolean isAvailable = true;
        for(HouseReservation h: houseReservations){
            Long startDateMilis = h.getStartDate().getTime();
            Long endDateMilis = h.getEndDate().getTime();
            isAvailable = isAvailable(reservationCheckDTO, isAvailable, h, startDateMilis, endDateMilis);
            if(!isAvailable){
                break;
            }
        }
        return isAvailable;
    }

    private boolean isAvailable(ReservationCheckDTO reservationCheckDTO, boolean isAvailable, HouseReservation h, Long startDateMilis, Long endDateMilis) {
        isAvailable = false;
        if(!(h.isAction()&& h.isAvailable() ==true) || h.getCancelled() == true){
            if(!(reservationCheckDTO.getStartMilis() <= startDateMilis && reservationCheckDTO.getEndMilis() >= endDateMilis)){
                if(!checkIsBetween(reservationCheckDTO, startDateMilis, endDateMilis)){
                    isAvailable = true;
                }
            }
        }else{
            isAvailable = isAvailableAction(reservationCheckDTO, h, startDateMilis, endDateMilis);
        }

        return isAvailable;
    }

    private boolean isAvailableAction(ReservationCheckDTO reservationCheckDTO, HouseReservation h, Long startDateMilis, Long endDateMilis) {
        boolean isAvailable = true;
        if(h.isAction() && h.isAvailable() == true){
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
}
