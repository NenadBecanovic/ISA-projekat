package com.application.bekend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.application.bekend.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	Company getCompanyById(Long id);

}
