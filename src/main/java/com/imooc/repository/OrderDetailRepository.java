package com.imooc.repository;

import com.imooc.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author yuhe
 * @date 2021/11/17 16:22
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail ,String> {


    List<OrderDetail> findByOrderId(String orderId);
}
