package com.application.bekend.repository;

import com.application.bekend.model.RequestForAccountDeleting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestForAccountDeletingRepository extends JpaRepository<RequestForAccountDeleting, Long> {


}
