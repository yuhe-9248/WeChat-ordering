package com.imooc.repository;

import com.imooc.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yuhe
 * @date 2021/11/17 16:19
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    //根据买家的openid来查
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);




}
