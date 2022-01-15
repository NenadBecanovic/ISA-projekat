package com.application.bekend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.bekend.model.UserCategory;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Integer> {

	UserCategory findUserCategoryByName(String string);

	UserCategory getUserCategoryById(int id);

}
