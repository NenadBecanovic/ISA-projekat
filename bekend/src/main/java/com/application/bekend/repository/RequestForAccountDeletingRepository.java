package com.application.bekend.repository;

import com.application.bekend.model.RequestForAccountDeleting;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestForAccountDeletingRepository extends JpaRepository<RequestForAccountDeleting, Long> {

	@Query("select r from RequestForAccountDeleting r join fetch r.user u")
	public List<RequestForAccountDeleting> getAllRequests();
	
	public RequestForAccountDeleting findRequestForAccountDeletingById(Long id);
}
