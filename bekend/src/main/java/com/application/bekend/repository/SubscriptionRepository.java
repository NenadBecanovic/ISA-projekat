package com.application.bekend.repository;

import com.application.bekend.model.Room;
import com.application.bekend.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Subscription findSubscriptionBySubscribedUser_IdAndOwner_Id(Long userId, Long ownerId);

    List<Subscription> findAllBySubscribedUser_Id(Long id);

    void deleteById(Long id);
}
