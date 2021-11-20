package com.application.bekend.repository;

import com.application.bekend.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    MyUser findMyUserByEmailAndPassword(String email, String password);

    MyUser findMyUserByEmailOrUsername(String email, String password);

    MyUser findMyUserById(Long id);
}
