package com.technoelevate.order_service.entity;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_details")
@JsonInclude(value = Include.NON_DEFAULT)
public class Order implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GeneratedValue(generator = "orderIdGenerator")
//	@GenericGenerator(name = "orderIdGenerator",strategy ="com.technoelevate.order_service.ulitily.OrderIdGenerator" )
	private long id;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderId;

	private long productId;

	private double unitPrice;
	
	private double discount;

	private long quentity;

	private double totalPrice;

	private long userId;
	
}
