package com.imooc.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yuhe
 * @date 2021/11/17 13:24
 *
 * 商品详情
 */

@Data
public class ProductInfoVO implements Serializable {


    private static final long serialVersionUID = -3895834204864685262L;

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;   //商品名字

    @JsonProperty("price")
    private BigDecimal productPrice;  //商品价格

    @JsonProperty("description")
    private String productDescription;   //商品描述

    @JsonProperty("icon")
    private String productIcon;   //商品小图
}
