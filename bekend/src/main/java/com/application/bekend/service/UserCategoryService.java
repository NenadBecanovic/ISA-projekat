package com.application.bekend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.bekend.DTO.UserCategoryDTO;
import com.application.bekend.model.MyUser;
import com.application.bekend.model.UserCategory;
import com.application.bekend.repository.UserCategoryRepository;

@Service
public class UserCategoryService {
	
	private final UserCategoryRepository userCategoryRepository;
	private final MyUserService myUserService;
	
	@Autowired
	public UserCategoryService(UserCategoryRepository userCategoryRepository, MyUserService myUserService) {
		this.userCategoryRepository = userCategoryRepository;
		this.myUserService = myUserService;
	}

	public List<UserCategory> findAll() {
		// TODO Auto-generated method stub
		return this.userCategoryRepository.findAll();
	}
	
	public boolean addCategory(UserCategoryDTO dto) {
		UserCategory newCategory = new UserCategory();
		newCategory.setName(dto.getName());
		newCategory.setDiscountPercentage(dto.getDiscountPercentage());
		newCategory.setPoints(dto.getPoints());
		this.save(newCategory);
		return true;
	}
	
	public void save(UserCategory userCategory) {
		this.userCategoryRepository.save(userCategory);
	}

	public boolean delete(int id) {
		List<MyUser> allUsers = this.myUserService.getAllUsers();
		UserCategory deletedCategory = this.userCategoryRepository.getById(id);	
		List<UserCategory> allCategories = this.userCategoryRepository.findAll();
		for(MyUser user: allUsers) {
			int min = 0;
			UserCategory cat = new UserCategory();
			for(UserCategory category: allCategories) {
				if(category.getId() == id) {
					continue;
				}
				if(category.getPoints() > min && user.getPoints() > category.getPoints()) {
					min = category.getPoints();
					cat = category;
				}
			}
			user.setCategory(cat);
			this.myUserService.save(user);
		}
		this.userCategoryRepository.delete(deletedCategory);
		return true;
	}

	public boolean edit(int id, UserCategoryDTO dto) {
		UserCategory category = this.userCategoryRepository.getUserCategoryById(id);
		category.setName(dto.getName());
		category.setDiscountPercentage(dto.getDiscountPercentage());
		category.setPoints(dto.getPoints());
		this.userCategoryRepository.save(category);
		return true;
	}
	
	public UserCategory findUserCategoryByName(String name) {
		return this.userCategoryRepository.findUserCategoryByName(name);
	}
}
