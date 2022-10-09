package com.example.yourhealth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.yourhealth.repository.TrainingMenuRepository;

@Service
public class TrainingMenuService {

    @Autowired
    private TrainingMenuRepository trainingMenuRepository;
    
    
    
    /**
     * トレーニングメニュー情報 主キー検索
     * @return 検索結果
     */
//    public TrainingMenu findById(Long id) {
//      return trainingMenuRepository.findAll(id).get();
//    }

    
}