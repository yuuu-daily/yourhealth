package com.example.yourhealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.yourhealth.entity.WeightData;

/* DBアクセスのための入り口
   全件取得するためのインターフェースのクラス*/

@Repository
public interface WeightDataRepository extends JpaRepository<WeightData, Long> {

    Iterable<WeightData> findAllByOrderByUpdatedAtDesc();
}
