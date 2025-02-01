package com.storesmanagementsystem.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storesmanagementsystem.order.client.ProductServiceClient;
import com.storesmanagementsystem.order.client.UserServiceClient;
import com.storesmanagementsystem.order.contracts.*;
import com.storesmanagementsystem.order.domain.OrderDetails;
import com.storesmanagementsystem.order.repo.OrderRepository;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    UserServiceClient userServiceClient;

    @Autowired
    ProductServiceClient productServiceClient;

    @Autowired
    OrderRepository ordersRepository;

    @Autowired
    ObjectMapper mapper;

    @SneakyThrows
    @Override
    public OrderInfoBean placeOrder(OrderInfoBean orderDetails) {
        OrderInfoBean orderResp = null;
        if (null != orderDetails.getUser() && null != orderDetails.getUser().getId()) {
            CompletableFuture<CommonResponse> userResp = CompletableFuture.supplyAsync(() -> userServiceClient.getUser(orderDetails.getUser().getId()))
                    .exceptionally(e -> {
                        throw new IllegalArgumentException(e.getMessage());
                    });

            orderResp = userResp.thenApply( userRep -> {
                OrderInfoBean order = null;
                try {
                    if (null != userRep.getUser()) {
                        UserInfoBean user = userRep.getUser();
                        if (user.getRole().contains("DEALER")) {
                            CompletableFuture<CommonResponse> productResp = CompletableFuture.supplyAsync(() -> productServiceClient.getProduct(orderDetails.getProductId())).exceptionally(e -> {
                                throw new IllegalArgumentException(e.getMessage());
                            });
                            order = processDealerOrder(orderDetails, userResp.get().getUser(), productResp.get().getProduct());
                            return order;
                        } else if (user.getRole().contains("CUSTOMER")) {
                            CommonResponse prodResp = productServiceClient.getDealerProd(orderDetails.getProductId(),
                                    orderDetails.getDealerId());
                            order = processCustomerOrder(orderDetails, user, prodResp);
                            return order;
                        } else {
                            throw new IllegalArgumentException("User not found");
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
                return order;
            }
            ).get();
            return orderResp;
//					.thenApply(resp -> {
//						UserInfoBean user = resp.getUser();
//						if (null != user) {
//							if (user.getRole().contains("DEALER")) {
//								return processDealerOrder(orderDetails, user);
//							} else if (user.getRole().contains("CUSTOMER")) {
//								return processCustomerOrder(orderDetails, user);
//							} else {
//								throw new IllegalArgumentException("User not found");
//							}
//
//						}
//						return orderResp;
//					});
        } else {
            throw new IllegalArgumentException("User Details are mandatory");
        }
    }

    private OrderInfoBean processDealerOrder(OrderInfoBean orderDetails, UserInfoBean user, ProductInfoBean product) {

        OrderInfoBean orderResp = null;
        if (null != product) {
            UserInfoBean toUser = new UserInfoBean();
            toUser.setId(user.getId());
            DealerProductInfoBean toUpdate = new DealerProductInfoBean();
            List<DealerProductInfoBean> products = new ArrayList<>();
            toUser.setDealersProds(products);
            toUser.getDealersProds().add(toUpdate);
            toUpdate.setProductId(orderDetails.getProductId());
            toUpdate.setQuantity(orderDetails.getQuantity());
            if (product.getQuantity() > orderDetails.getQuantity()) {
                String status = productServiceClient.addDealerProduct(toUser);
                if (null != status) {
                    OrderDetails order = new OrderDetails();
                    LocalDate date = LocalDate.now();
                    order.setUserId(user.getId());
                    order.setDateOfOrder(date);
                    order.setProductId(orderDetails.getProductId());
                    order.setProductName(product.getProductName());
                    if (null != orderDetails.getPaymentType())
                        order.setPaymentType(PaymentMode.valueOf(orderDetails.getPaymentType()).name());
                    else
                        order.setPaymentType(PaymentMode.OFFLINE.name());
                    order.setStatus("Not yet Delivered");
                    order.setAmount(product.getProductCost() * orderDetails.getQuantity());
                    order.setRole(user.getRole());
                    order.setQuantity(orderDetails.getQuantity());
                    order.setDateOfDelivery(date.plusDays(2));
                    System.out.println(order);
                    OrderDetails save = ordersRepository.save(order);
                    orderResp = mapper.convertValue(save, OrderInfoBean.class);
                    return orderResp;
                }

            } else {
                throw new IllegalArgumentException("Specified quantity is more than available quantity");
            }
        } else {
            throw new IllegalArgumentException("Product not found");
        }
//		CommonResponse productResponse = productServiceClient.getProduct(orderDetails.getProductId());
//		ProductInfoBean product = productResponse.getProduct();
//		if (null != product) {
//			UserInfoBean toUser = new UserInfoBean();
//			toUser.setId(user.getId());
//			DealerProductInfoBean toUpdate = new DealerProductInfoBean();
//			List<DealerProductInfoBean> products = new ArrayList<>();
//			toUser.setDealersProds(products);
//			toUser.getDealersProds().add(toUpdate);
//			toUpdate.setProductId(orderDetails.getProductId());
//			toUpdate.setQuantity(orderDetails.getQuantity());
//			if (product.getQuantity() > orderDetails.getQuantity()) {
//				String status = productServiceClient.addDealerProduct(toUser);
//				if (null != status) {
//					OrderDetails order = new OrderDetails();
//					LocalDate date = LocalDate.now();
//					order.setUserId(user.getId());
//					order.setDateOfOrder(date);
//					order.setProductId(orderDetails.getProductId());
//					order.setProductName(product.getProductName());
//					if (null != orderDetails.getPaymentType())
//						order.setPaymentType(PaymentMode.valueOf(orderDetails.getPaymentType()).name());
//					else
//						order.setPaymentType(PaymentMode.OFFLINE.name());
//					order.setStatus("Not yet Delivered");
//					order.setAmount(product.getProductCost() * orderDetails.getQuantity());
//					order.setRole(user.getRole());
//					order.setQuantity(orderDetails.getQuantity());
//					order.setDateOfDelivery(date.plusDays(2));
//					OrderDetails save = ordersRepository.save(order);
//					orderResp = mapper.convertValue(save, OrderInfoBean.class);
//					return orderResp;
//				} else {
//					throw new IllegalArgumentException("Failed to create order");
//				}
//
//			} else {
//				throw new IllegalArgumentException("Specified quantity is more than available quantity");
//			}
//		} else {
//			throw new IllegalArgumentException("Product not found");
//		}
        return orderResp;
    }

    private OrderInfoBean processCustomerOrder(OrderInfoBean orderDetails, UserInfoBean user, CommonResponse prodResp ) {
        OrderInfoBean orderResp = null;
        if (null != prodResp && null != prodResp.getDealerProd()) {
            if (prodResp.getDealerProd().getQuantity() > orderDetails.getQuantity()) {
                DealerProductInfoBean toUpdate = new DealerProductInfoBean();
                toUpdate.setId(prodResp.getDealerProd().getId());
                toUpdate.setQuantity(orderDetails.getQuantity());
                String updateSalesQuantity = productServiceClient.updateDealerProduct("ORDER_UPDATE_SALES_QUANTITY",
                        toUpdate);
                if (null != updateSalesQuantity) {
                    OrderDetails order = new OrderDetails();
                    LocalDate date = LocalDate.now();
                    order.setUserId(user.getId());
                    order.setDateOfOrder(date);
                    order.setProductId(orderDetails.getProductId());
                    order.setProductName(prodResp.getDealerProd().getProductName());
                    if (null != orderDetails.getPaymentType())
                        order.setPaymentType(PaymentMode.valueOf(orderDetails.getPaymentType()).name());
                    else
                        order.setPaymentType(PaymentMode.OFFLINE.name());
                    order.setStatus("Not yet Delivered");
                    order.setAmount(prodResp.getDealerProd().getSellingPrice() * orderDetails.getQuantity());
                    order.setRole(user.getRole());
                    order.setQuantity(orderDetails.getQuantity());
                    order.setDateOfDelivery(date.plusDays(3));
                    OrderDetails save = ordersRepository.save(order);
                    orderResp = mapper.convertValue(save, OrderInfoBean.class);
                    return orderResp;
                }
            } else {
                throw new IllegalArgumentException("Specified quantity is more than available quantity");
            }
            return null;
        }
        return null;
    }

    @Override
    public OrderInfoBean getPaymentDeatils(int orderId) {
        return null;
    }

    @Override
    public List<OrderInfoBean> getOrders(int userId) {
        List<OrderDetails> allByUserId = ordersRepository.findAllByUserId(userId);
        if (null != allByUserId && !allByUserId.isEmpty()) {
            return allByUserId.stream().map(order -> mapper.convertValue(order, OrderInfoBean.class))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public OrderInfoBean getOrder(Integer orderId) {
        Optional<OrderDetails> byId = ordersRepository.findById(orderId);
        if (byId.isPresent()) {
            return mapper.convertValue(byId.get(), OrderInfoBean.class);
        } else {
            throw new IllegalArgumentException("Order Details not found");
        }
    }

}
