package com.application.bekend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.bekend.DTO.CompanyDTO;
import com.application.bekend.DTO.UserCategoryDTO;
import com.application.bekend.model.Company;
import com.application.bekend.model.UserCategory;
import com.application.bekend.repository.CompanyRepository;

@Service
public class CompanyService {
	
	private final CompanyRepository companyRepository;
	private final UserCategoryService userCategoryService;
	
	@Autowired
	public CompanyService(CompanyRepository companyRepository, UserCategoryService userCategoryService) {
		this.companyRepository = companyRepository;
		this.userCategoryService = userCategoryService;
	}

	public CompanyDTO getCompanyInfo(Long id) {
		Company company = this.companyRepository.getCompanyById(id);
		List<UserCategory> userCategories = this.userCategoryService.findAll();
		CompanyDTO companyDTO = new CompanyDTO(company.getPercentagePerReservation(), company.getPointsPerReservationClient(), company.getPointsPerReservationOwner());
		for(UserCategory category: userCategories) {
			companyDTO.addUserCategory(new UserCategoryDTO(category.getId(), category.getName(), category.getDiscountPercentage(), category.getPoints()));
		}
		
		return companyDTO;
	}

	public List<UserCategoryDTO> getAllUserCategories() {
		List<UserCategory> userCategories = this.userCategoryService.findAll();
		List<UserCategoryDTO> userCategoriesDTO = new ArrayList<UserCategoryDTO>();
		for(UserCategory category: userCategories) {
			userCategoriesDTO.add(new UserCategoryDTO(category.getId(), category.getName(), category.getDiscountPercentage(), category.getPoints()));
		}
		return userCategoriesDTO;
	}

	public boolean addCategory(UserCategoryDTO dto) {
		return this.userCategoryService.addCategory(dto);
	}

	public boolean deleteCategory(int id) {
		return this.userCategoryService.delete(id);
	}

	public boolean editCategory(int id, UserCategoryDTO dto) {
		return this.userCategoryService.edit(id, dto);
	}

	public boolean saveChanges(Long id, CompanyDTO dto) {
		Company company = this.companyRepository.getCompanyById(id);
		company.setPercentagePerReservation(dto.getPercentagePerReservation());
		company.setPointsPerReservationClient(dto.getPointsPerReservationClient());
		company.setPointsPerReservationOwner(dto.getPointsPerReservationOwner());
		this.companyRepository.save(company);
		
		return true;
	}
}
