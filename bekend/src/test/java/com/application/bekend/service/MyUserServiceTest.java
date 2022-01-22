package com.application.bekend.service;

import com.application.bekend.DTO.AddressDTO;
import com.application.bekend.DTO.MyUserDTO;
import com.application.bekend.DTO.ReservationCheckDTO;
import com.application.bekend.model.Address;
import com.application.bekend.model.BoatReservation;
import com.application.bekend.model.MyUser;
import com.application.bekend.model.Subscription;
import com.application.bekend.repository.BoatReservationRepository;
import com.application.bekend.repository.MyUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyUserServiceTest {

    @Mock
    private MyUserRepository myUserRepositoryMock;

    @Mock
    private MyUser myUser;

    @InjectMocks
    private MyUserService myUserService;



    @Test
    public void findAll() {
        when(myUserRepositoryMock.findAll()).thenReturn(Arrays.asList(myUser));
        List<MyUser> subs = myUserService.getAllUsers();
        assertThat(subs).hasSize(1);
        verify(myUserRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(myUserRepositoryMock);
    }
}
