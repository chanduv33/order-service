package com.storesmanagementsystem.order.contracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoBean {

	private Integer id;

	private String name;

	private String role;

	private String username;

	private String password;

	private Long mobileNumber;

	private List<Address> address = new ArrayList<>();

	private List<OrderInfoBean> orders ;

	private List<ProductInfoBean> products ;

	private List<DealerProductInfoBean> dealersProds ;

}
