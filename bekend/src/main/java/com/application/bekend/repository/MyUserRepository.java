package com.application.bekend.repository;

import com.application.bekend.model.AdditionalServices;
import com.application.bekend.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    MyUser findMyUserByEmailAndPassword(String email, String password);

    MyUser findMyUserByEmailOrUsername(String email, String password);

    MyUser findMyUserById(Long id);

    MyUser findMyUserByUsername(String username);

    @Query("select user from MyUser user join fetch user.houseReservations houseReservations where houseReservations.house.id = ?1")
    Set<MyUser> getAllByHouseId(Long id);

    @Query("select user from MyUser user join fetch user.houseReservations houseReservations where houseReservations.id = ?1")
    MyUser findMyUserByHouseReservationId(Long id);

}