package com.example.yourhealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.yourhealth.entity.Weightdata;

@Repository
public interface WeightdataRepository extends JpaRepository<Weightdata, Long> {

    Iterable<Weightdata> findAllByOrderByUpdatedAtDesc();
}
