package com.imooc.exception;

import com.imooc.enums.ResultEnum;
import lombok.Data;

/**
 * @author yuhe
 * @date 2021/11/19 12:26
 */

@Data
public class SellException extends RuntimeException{


    private Integer code;

    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public SellException(Integer code,String message){
        super(message);
        this.code = code;
    }

}
