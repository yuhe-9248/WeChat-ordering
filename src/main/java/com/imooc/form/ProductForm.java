package com.imooc.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author yuhe
 * @date 2021/11/26 17:14
 *
 * product表单提交数据的对象
 */

@Data
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS",timezone = "GMT+8")//如果不加，数据库显示时间与真实时间相差8小时
public class ProductForm {


    private String productId;

    //名字
    private String productName;

    //单价
    private BigDecimal productPrice;

    //库存
    private Integer productStock;

    //描述
    private String productDescription;

    //小图
    private String productIcon;

    //类目编号
    private Integer CategoryType;
}
