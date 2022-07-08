package com.imooc.dto;

import lombok.Getter;

/**
 * @author yuhe
 * @date 2021/11/19 13:47
 *
 * 购物车
 */
@Getter
public class CartDTO {

    //商品id
    private String productId;
    //商品数量
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
