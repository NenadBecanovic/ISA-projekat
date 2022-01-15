package com.application.bekend.service;

import com.application.bekend.DTO.BoatReservationDTO;
import com.application.bekend.DTO.HouseReservationDTO;
import com.application.bekend.model.BoatReservation;
import com.application.bekend.model.HouseReservation;
import com.application.bekend.repository.HouseReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
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
}
