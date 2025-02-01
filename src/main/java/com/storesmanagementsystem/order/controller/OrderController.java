package com.storesmanagementsystem.order.controller;

import com.storesmanagementsystem.order.contracts.CommonResponse;
import com.storesmanagementsystem.order.contracts.OrderInfoBean;
import com.storesmanagementsystem.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping(path = "/Order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> placeOrder(@RequestBody OrderInfoBean orderInfoBean) {
        CommonResponse respStructure = getRespStructure();
        OrderInfoBean orderInfo = orderService.placeOrder(orderInfoBean);
        if (null != orderInfo) {
            respStructure.setOrder(orderInfo);
            return ResponseEntity.ok().body(respStructure);
        } else {
            throw new IllegalArgumentException("Failed to place order");
        }
    }

    @GetMapping(path = "/Order",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getOrders(@RequestParam(value = "userId", required = true) Integer userId) {
        CommonResponse respStructure = getRespStructure();
        List<OrderInfoBean> orders = orderService.getOrders(userId);
        if (null != orders) {
            respStructure.setOrders(orders);
            return ResponseEntity.ok().body(respStructure);
        } else {
            throw new IllegalArgumentException("Failed to get orders");
        }
    }

    @GetMapping(path = "/Order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getOrder(@PathVariable(value = "id", required = true) Integer orderId) {
        CommonResponse respStructure = getRespStructure();
        OrderInfoBean order = orderService.getOrder(orderId);
        if (null != order) {
            respStructure.setOrder(order);
            return ResponseEntity.ok().body(respStructure);
        } else {
            throw new IllegalArgumentException("Failed to get orders");
        }
    }

    private CommonResponse getRespStructure() {
        return new CommonResponse();
    }
}
