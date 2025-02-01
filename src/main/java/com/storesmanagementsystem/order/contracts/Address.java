package com.storesmanagementsystem.order.contracts;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address {

    private Integer id;

    private String addressLine1;

    private String addressLine2;

    private String street;

    private String city;

    private String state;

    private String country;

    private Integer pincode;

    private String addressType;
}
