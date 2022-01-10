package com.application.bekend.repository;

import com.application.bekend.model.Feedback;
import com.application.bekend.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {


}
