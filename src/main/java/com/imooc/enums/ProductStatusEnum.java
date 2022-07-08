package com.imooc.enums;

import lombok.Getter;

/**
 * @author yuhe
 * @date 2021/11/16 21:34
 *
 * 商品状态
 */
@Getter
public enum ProductStatusEnum implements CodeEnum {

    UP(0,"在架"),
    DOWN(1,"已下架")
    ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code,String message) {
        this.code = code;
        this.message = message;
    }


}
