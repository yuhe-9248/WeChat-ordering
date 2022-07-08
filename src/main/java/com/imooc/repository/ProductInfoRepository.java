package com.imooc.repository;

import com.imooc.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author yuhe
 * @date 2021/11/16 20:49
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    //根据商品状态查询商品
    List<ProductInfo> findByProductStatus(Integer productStatus);








}
