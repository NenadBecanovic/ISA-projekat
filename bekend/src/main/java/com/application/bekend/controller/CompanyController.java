package com.application.bekend.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.bekend.DTO.CompanyDTO;
import com.application.bekend.DTO.FeedbackDTO;
import com.application.bekend.DTO.UserCategoryDTO;
import com.application.bekend.service.CompanyService;

@RestController
@RequestMapping("api/company")
public class CompanyController {
	
	private final CompanyService companyService;
	
	@Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
	
	@GetMapping("/getCompanyInfo/{id}")
    public ResponseEntity<CompanyDTO> getCompanyInfo(@PathVariable("id") Long id){
        CompanyDTO company = this.companyService.getCompanyInfo(id);
        
        return new ResponseEntity<>(company, HttpStatus.OK);
    }
	
	@GetMapping("/getCompanyPercentage/{id}")
    public ResponseEntity<Integer> getCompanyPercentage(@PathVariable("id") Long id){
        CompanyDTO company = this.companyService.getCompanyInfo(id);
        
        return new ResponseEntity<>(company.getPercentagePerReservation(), HttpStatus.OK);
    }
	
	@GetMapping("/getAllUserCategories")
    public ResponseEntity<List<UserCategoryDTO>> getAllUserCategories(){
        List<UserCategoryDTO> userCategories = this.companyService.getAllUserCategories();
        
        return new ResponseEntity<>(userCategories, HttpStatus.OK);
    }
	
	@PostMapping("/addCategory")
    public ResponseEntity<Boolean> addCategory(@RequestBody UserCategoryDTO dto){
        boolean isAdded = this.companyService.addCategory(dto);
        
        return new ResponseEntity<>(isAdded, HttpStatus.OK);
    }
	
	@DeleteMapping("/delete/{id}")
	@Transactional
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("id") int id){
        boolean isDeleted = this.companyService.deleteCategory(id);
        
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }
	
	@PutMapping("/edit/{id}")
    public ResponseEntity<Boolean> editCategory(@PathVariable("id") int id, @RequestBody UserCategoryDTO dto){
        boolean isDeleted = this.companyService.editCategory(id, dto);
        
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }
	
	@PutMapping("/saveChanges/{id}")
    public ResponseEntity<Boolean> saveChanges(@PathVariable("id") Long id,@RequestBody CompanyDTO dto){
        boolean isSaved = this.companyService.saveChanges(id, dto);
        
        return new ResponseEntity<>(isSaved, HttpStatus.OK);
    }
	
	@GetMapping("/getUserCategory/{email}")
    public ResponseEntity<UserCategoryDTO> getCompanyInfo(@PathVariable("email") String email){
		UserCategoryDTO category = this.companyService.getUserCategory(email);
        
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
