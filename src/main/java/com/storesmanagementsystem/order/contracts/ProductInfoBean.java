package com.storesmanagementsystem.order.contracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInfoBean {

	private Integer id;

	private String productName;

	private Double productCost;

	private String description;

	private String imageUrl;

	private Integer quantity;

	private String manufacturerName;

}
