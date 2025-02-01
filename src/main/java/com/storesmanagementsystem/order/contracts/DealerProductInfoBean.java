package com.storesmanagementsystem.order.contracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DealerProductInfoBean {

	private Integer id;

	private Double sellingPrice;

	private Integer quantity;

	private Integer productId;

	private Integer minimumQuantity;

	private String imageUrl;

	private String productName;

	private String manufacturerName;

	private ProductInfoBean product;
}
