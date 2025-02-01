package com.storesmanagementsystem.order.contracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse {
	private List<UserInfoBean> users;
	private List<ProductInfoBean> products;
	private List<OrderInfoBean> orders;
	private OrderInfoBean order;
	private UserInfoBean user;
	private ProductInfoBean product;
	private DealerProductInfoBean dealerProd;
	private List<DealerProductInfoBean> dealerProds;
	private List<Error> errors;
}
