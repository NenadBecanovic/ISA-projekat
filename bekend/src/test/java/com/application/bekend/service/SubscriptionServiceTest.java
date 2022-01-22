package com.application.bekend.service;

import com.application.bekend.model.House;
import com.application.bekend.model.MyUser;
import com.application.bekend.model.Subscription;
import com.application.bekend.repository.SubscriptionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static com.application.bekend.constants.HouseConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepositoryMock;

    @Mock
    private Subscription subMock;

    @Mock
    private MyUser myUser;

    @InjectMocks
    private SubscriptionService subscriptionServiceMock;
    @Test
    public void checkIfUserIsSubscribed() {
        when(subscriptionRepositoryMock.findSubscriptionBySubscribedUser_IdAndOwner_Id(1L,1L)).thenReturn(null);
        Boolean isSubscribed = this.subscriptionServiceMock.checkIfUserIsSubscribed(1L,1L);
        assertEquals(isSubscribed, false);
        verify(subscriptionRepositoryMock, times(1)).findSubscriptionBySubscribedUser_IdAndOwner_Id(1L, 1L);
        verifyNoMoreInteractions(subscriptionRepositoryMock);
    }

    @Test
    public void findAllBySubscribedUserId() {
        MyUser subscribedUser = new MyUser();
        subscribedUser.setId(1L);
        Subscription sub = new Subscription(1L, subscribedUser, new MyUser());
        Subscription sub2= new Subscription(2L,subscribedUser, new MyUser());
        when(subscriptionRepositoryMock.findAllBySubscribedUser_Id(1L)).thenReturn(Arrays.asList(sub, sub2));
        List<Subscription> subs = subscriptionServiceMock.findAllBySubscribedUserId(1L);
        assertThat(subs).hasSize(2);
        verify(subscriptionRepositoryMock, times(1)).findAllBySubscribedUser_Id(1L);
        verifyNoMoreInteractions(subscriptionRepositoryMock);
    }


    @Test
    @Transactional
    @Rollback(true) // uključeno po default-u, ne mora se navesti
    public void testAdd() {
        MyUser subscribedUser = new MyUser();
        subscribedUser.setId(1L);
        MyUser owner = new MyUser();
        owner.setId(2L);
        Subscription subscription = new Subscription();
        subscription.setId(2L);
        subscription.setOwner(owner);
        subscription.setSubscribedUser(subscribedUser);

        MyUser owner1 = new MyUser();
        owner1.setId(3L);
        Subscription subscription1 = new Subscription();
        subscription1.setId(1L);
        subscription1.setOwner(owner1);
        subscription1.setSubscribedUser(subscribedUser);

        // 1. Definisanje ponašanja mock objekata
        when(subscriptionRepositoryMock.findAllBySubscribedUser_Id(1L)).thenReturn(Arrays.asList(subscription1));
        when(subscriptionRepositoryMock.save(subscription)).thenReturn(subscription);

        // 2. Akcija
        int dbSizeBeforeAdd = subscriptionServiceMock.findAllBySubscribedUserId(1L).size();

        Subscription sub = subscriptionServiceMock.save(subscription);

        when(subscriptionRepositoryMock.findAllBySubscribedUser_Id(1L)).thenReturn(Arrays.asList(subscription1, subscription));
        assertThat(sub).isNotNull();

        List<Subscription> subs = subscriptionServiceMock.findAllBySubscribedUserId(1L);
        assertThat(subs).hasSize(dbSizeBeforeAdd + 1); //verifikacija da je nova vikendica upisan u bazu

        sub = subs.get(subs.size() - 1); // preuzimanje poslednje vikendice

        assertThat(sub.getId()).isEqualTo(2L);
        assertThat(sub.getSubscribedUser().getId()).isEqualTo(1L);
        assertThat(sub.getOwner().getId()).isEqualTo(2L);

        verify(subscriptionRepositoryMock, times(2)).findAllBySubscribedUser_Id(1L);
        verify(subscriptionRepositoryMock, times(1)).save(subscription);
        verifyNoMoreInteractions(subscriptionRepositoryMock);
    }
}
