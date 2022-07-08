package com.imooc.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imooc.enums.ProductStatusEnum;
import com.imooc.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yuhe
 * @date 2021/11/16 20:32
 *
 *
 * 商品
 */


@Entity
@Data
@DynamicUpdate
//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS",timezone = "GMT+8")//如果不加，数据库显示时间与真实时间相差8小时
public class ProductInfo implements Serializable {
    private static final long serialVersionUID = 1778270004321270113L;

    @Id
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

    //状态 0正常  1下架
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    //类目编号
    private Integer CategoryType;


    private Date createTime;

    private Date updateTime;

    @JsonIgnore//对象转换为json格式忽略
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getByCode(productStatus,ProductStatusEnum.class);
    }
}
