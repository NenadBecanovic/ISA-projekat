package com.application.bekend.repository;

import com.application.bekend.model.Appeal;
import com.application.bekend.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface AppealRepository extends JpaRepository<Appeal, Long> {


}
