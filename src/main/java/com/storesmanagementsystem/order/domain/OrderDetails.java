package com.storesmanagementsystem.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.ToString.Exclude;

import javax.persistence.*;
import java.time.LocalDate;

@SuppressWarnings("unused")
@Entity
@Data
@Table(name = "orders_info")
public class OrderDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;
	
	@Column(nullable = false)
	private String role;
	
	@Column(nullable = false)
	private double amount;
	
	@Column(nullable = false)
	private String paymentType;
	
	@Column(nullable = false)
	private int quantity;
	
	@Column
	private int productId;

//	@Column(nullable = false)
//	private String orderType;
	
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate deliveredOn ;
	
	@Column
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfOrder;
	
	@Column
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfDelivery;
	
	@Column(nullable = false)
	private String productName;
	
	@Column
	private String status;
	
	
	@Column
	private Integer userId;
	
}
