package com.example.yourhealth.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.yourhealth.entity.TrainingMenu;
import com.example.yourhealth.repository.TrainingMenuRepository;
import com.example.yourhealth.request.TrainingMenuUpdateRequest;

@Service
public class TrainingMenuService {

    @Autowired
    private TrainingMenuRepository repository;
    
    /**
     * メニュー情報 全検索
     * @return 検索結果
     */
    public List<TrainingMenu> searchAll() {
      return repository.findAll();
    }
    /**
     * メニュー情報 主キー検索
     * @return 検索結果
     */
    public TrainingMenu findById(Long id) {
      return repository.findById(id);
    }
    
    /**
     * メニュー情報 更新
     * @param user メニュー情報
     */
    public void update(TrainingMenuUpdateRequest menuUpdateRequest) {
      TrainingMenu menu = findById(menuUpdateRequest.getId());
      menu.setCategory(menuUpdateRequest.getCategory());
      menu.setTitle(menuUpdateRequest.getTitle());
      menu.setDescription(menuUpdateRequest.getDescription());
      menu.setUpdatedAt(new Date());
      repository.save(menu);
    }
    
    /**
     * メニュー情報 物理削除
     * @param id ID
     */
    public void delete(Long id) {
      TrainingMenu menu = findById(id);
      repository.delete(menu);
    }

}
