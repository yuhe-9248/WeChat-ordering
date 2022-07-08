package com.imooc.enums;

import lombok.Getter;

/**
 * @author yuhe
 * @date 2021/11/17 15:24
 */

@Getter
public enum OrderStatesEnum implements CodeEnum {
    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"已取消")
    ;

    private Integer code;
    private String message;

    OrderStatesEnum(Integer code,String msg){
        this.code = code;
        this.message = msg;
    }





}
