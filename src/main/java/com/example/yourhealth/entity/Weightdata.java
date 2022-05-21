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

@Entity
@Table(name = "weight_histories")
@Data
public class Weightdata extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "weightdata_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    
    @Column(nullable = false)
    private BigDecimal weight;
    
    public Weightdata() {
        super();
    }
    
    public Weightdata(Long id, BigDecimal weight) {
    	this.userId = id;
    	this.weight = weight;
    }
    
    public  Long getId() {
		return id;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	
    
}