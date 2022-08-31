package com.example.yourhealth.entity;

import java.io.Serializable;

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
@Table(name = "training_menus")
@Data
public class TrainingMenu extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "trainingMenu_id_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long userId;
	
	@ManyToOne
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	private User user;
	
	@Column(nullable = false)
	private String category;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String description;
	
	public TrainingMenu() {
		super();
	}
	
	// コンストラクター
	public TrainingMenu(Long id, String category, String title, String description) {
		this.userId = id;
		this.category = category;
		this.title = title;
		this.description = description;
	}
	
	public Long getId() {
		return id;
	}
	public String getCategory () {
		return category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	
}
