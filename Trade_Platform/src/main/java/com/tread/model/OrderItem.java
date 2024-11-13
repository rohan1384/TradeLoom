package com.tread.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity

public class OrderItem {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private double quantity;
	
	@ManyToOne
	private Coin coin;
	
	private double buyPrice;
	
	private double sellPrice;
	
	@JsonIgnore
	@OneToOne
	private Order order;
	
}
