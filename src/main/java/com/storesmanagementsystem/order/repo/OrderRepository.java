package com.storesmanagementsystem.order.repo;

import com.storesmanagementsystem.order.domain.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderDetails, Integer> {

    List<OrderDetails> findAllByUserId(int userId);
}
