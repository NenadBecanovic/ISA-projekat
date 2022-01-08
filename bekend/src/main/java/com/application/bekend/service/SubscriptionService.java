package com.application.bekend.service;

import com.application.bekend.model.Subscription;
import com.application.bekend.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription save(Subscription subscription){
        return this.subscriptionRepository.save(subscription);
    }

    public Boolean checkIfUserIsSubscribed(Long userId, Long ownerId){
        Subscription subscription = this.subscriptionRepository.findSubscriptionBySubscribedUser_IdAndOwner_Id(userId, ownerId);
        if(subscription == null){
            return false;
        }else{
            return true;
        }
    }

    public List<Subscription> findAllBySubscribedUserId(Long id){
        return this.subscriptionRepository.findAllBySubscribedUser_Id(id);
    }

    public void deleteSubscriptionById(Long id){
        this.subscriptionRepository.deleteById(id);
    }
}
