package com.imooc.service;

import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDTO;
import com.imooc.enums.ProductStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.repository.ProductInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Optional;

/**
 * @author yuhe
 * @date 2021/11/16 21:32
 */

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;


    /**
     * 查询一个商品
     * @param productId
     * @return
     */
    @Override
//    @ManyToMany(fetch = FetchType.EAGER)//不加这个注解，只加下面注解后会报错  org.hibernate.LazyInitializationException: null
//    @Cacheable(cacheNames = "product",key = "123")
    public ProductInfo findOne(String productId) {
//        return productInfoRepository.getOne(productId);
        return productInfoRepository.getById(productId);
    }

    //查询已经上架商品
    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    //查找所有
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    //保存
    @Override
//    @ManyToMany(fetch = FetchType.EAGER)//不加这个注解，只加下面注解后会报错  org.hibernate.LazyInitializationException: null
    @CachePut(cacheNames = "product",key = "123")
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    //加库存
    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList) {
            ProductInfo productInfo = productInfoRepository.getOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);

            productInfoRepository.save(productInfo);
        }
    }

    //减库存
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = productInfoRepository.getOne(cartDTO.getProductId());
            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();//先计算库存减购物车里面数量
            if (result < 0){ //小于0.说明不够。抛异常
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result); //不小于0.说明能减库存。就把减过的数量设置成库存
            productInfoRepository.save(productInfo);//更改数据库
        }
    }


    //上架
    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoRepository.getOne(productId);
        if (productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum().equals(ProductStatusEnum.UP)){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return  productInfoRepository.save(productInfo);
    }

    //下架
    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoRepository.getById(productId);
        if (productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return  productInfoRepository.save(productInfo);
    }
}
