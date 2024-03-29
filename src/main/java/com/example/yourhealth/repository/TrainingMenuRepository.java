package com.example.yourhealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.yourhealth.entity.TrainingMenu;


@Repository
public interface TrainingMenuRepository extends JpaRepository<TrainingMenu, String> {
	
	Iterable<TrainingMenu> findAllByOrderByUpdatedAtAsc();
	
    TrainingMenu findByIdAndUserId(Long id, Long userId);
    
    TrainingMenu findById(Long id);

//	void deleteById(Long id);

}

