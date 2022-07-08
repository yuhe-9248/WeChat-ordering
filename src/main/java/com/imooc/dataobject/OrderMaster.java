package com.imooc.dataobject;

import com.imooc.enums.OrderStatesEnum;
import com.imooc.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author yuhe
 * @date 2021/11/17 15:19
 *
 * 订单
 */

@Entity
@Data
@DynamicUpdate
public class OrderMaster {


    @Id
    //订单id
    private String orderId;

    //买家名字
    private String buyerName;

    //买家电话
    private String buyerPhone;

    //买家地址
    private String buyerAddress;

    //买家微信openid
    private String buyerOpenid;

    //订单总金额
    private BigDecimal orderAmount;

    //订单状态  默认为0，新订单
    private Integer orderStatus = OrderStatesEnum.NEW.getCode();

    //支付状态  默认为0，未支付
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;




}
