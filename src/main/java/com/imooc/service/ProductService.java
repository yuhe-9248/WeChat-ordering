package com.imooc.service;

import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuhe
 * @date 2021/11/16 21:27
 */


public interface ProductService {

    //根据id查询
    ProductInfo findOne(String productId);

    //查询已经上架的商品
    List<ProductInfo> findUpAll();

    //查询所有  用在服务端，东西太多，我们设置一个分页
    Page<ProductInfo> findAll(Pageable pageable);

    //保存
    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

    //上架
    ProductInfo onSale(String productId);

    //下架
    ProductInfo offSale(String productId);

}
