package com.example.yourhealth.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

/* エンティティ  DBとのやり取りを担う */
@Entity
@Table(name = "weight_histories")
@Data
public class WeightData extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "weightData_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 値を自動生成するアノテーション
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    
    @Column(nullable = false)
    private BigDecimal weight;
    
    // デフォルトコンストラクタ
    public WeightData() {
        super();
    }
    // DBから受け取ったデータを変数にセット
    public WeightData(Long id, BigDecimal weight) {
    	this.userId = id;
    	this.weight = weight;
    }
    
    /*
     *lombok効いていない？？
     */
    // データアクセスのためにゲッターを用意
    public Long getId() {
		return id;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	
    
}