package com.storesmanagementsystem.order.service;

import com.storesmanagementsystem.order.contracts.DealerProductInfoBean;
import com.storesmanagementsystem.order.contracts.OrderInfoBean;
import com.storesmanagementsystem.order.contracts.ProductInfoBean;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OrderService {
    public OrderInfoBean placeOrder(OrderInfoBean bean);

    public OrderInfoBean getPaymentDeatils(int orderId);

    public List<OrderInfoBean> getOrders(int userId);

    OrderInfoBean getOrder(Integer orderId);
}
