package com.imooc.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuhe
 * @date 2021/11/17 13:18
 *
 * 商品类目
 */

@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 7097863777546530545L;
    @JsonProperty("name")
    //此注解，将categoryName序列化返回给前端的时候，名字变为name
    private String categoryName;  //商品名字

    @JsonProperty("type")
    private Integer categoryType;  //商品类目

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
