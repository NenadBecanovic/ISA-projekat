package com.application.bekend.repository;

import com.application.bekend.model.AdditionalServices;
import com.application.bekend.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    MyUser findMyUserByEmailAndPassword(String email, String password);

    MyUser findMyUserByEmailOrUsername(String email, String password);

    MyUser findMyUserById(Long id);

    MyUser findMyUserByUsername(String username);

    MyUser findMyUserByEmail(String email);

    @Query("select user from MyUser user join fetch user.houseReservations houseReservations where houseReservations.house.id = ?1")
    Set<MyUser> getAllByHouseId(Long id);

    @Query("select user from MyUser user join fetch user.boatReservations boatReservations where boatReservations.boat.id = ?1")
    Set<MyUser> getAllByBoatId(Long id);

    @Query("select user from MyUser user join fetch user.houseReservations houseReservations where houseReservations.id = ?1")
    MyUser findMyUserByHouseReservationId(Long id);

    @Query("select user from MyUser user join fetch user.boatReservations boatReservations where boatReservations.id = ?1")
    MyUser findUserByBoatReservationId(Long id);

    @Query("select user from MyUser user join fetch user.houses house where house.id = ?1")
    MyUser findMyUserByHouseId(Long id);

    @Query("select user from MyUser user join fetch user.boats boat where boat.id = ?1")
    MyUser findMyUserByBoatId(Long id);
    
    @Query("select user from MyUser user join fetch user.adventureReservations adventureReservations where adventureReservations.id = ?1")
    MyUser findMyUserByAdventureReservationId(Long id);
    
    List<MyUser> findMyUserByIsActivated(boolean isActivated);

    @Query("select user from MyUser user join fetch user.authority auth where auth.name = 'ROLE_INSTRUCTOR'")
    List<MyUser> findAllInstructors();

    @Query("select user from MyUser user join fetch user.fishingAdventures adventure where adventure.id = ?1")
    MyUser findMyUserByAdventureId(Long id);
   // @Query("select * from MyUser u join fetch u.authorities a")
//	List<MyUser> findAllWithAuthorities();

   @Query("select user from MyUser user join fetch user.subscriptions_guests s where s.owner.id = ?1")
   List<MyUser> findSubscribedUsersByOwnerId(Long id);

    @Query("select user from MyUser user join fetch user.authority auth where auth.name = 'ROLE_USER'")
    List<MyUser> findAllUsers();


}


