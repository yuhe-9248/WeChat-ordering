package com.imooc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.dataobject.OrderDetail;
import com.imooc.enums.OrderStatesEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.utils.EnumUtil;
import com.imooc.utils.Serializer.Date2LongSerializer;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author yuhe
 * @date 2021/11/19 11:30
 */

@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)//已过期的方法，等于下面
@JsonInclude(JsonInclude.Include.NON_NULL)
//注解的作用：下面的参数如果有null，就不给前端返回了
public class OrderDTO {

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
    private Integer orderStatus = OrderStatesEnum.NEW.getCode();;

    //支付状态  默认为0，未支付
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    //创建时间
    @JsonSerialize(using = Date2LongSerializer.class)
    //注解的作用：告诉通过using 后面的类对下面的数据进行格式处理
    private Date createTime;

    //更新时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    //一个订单，可以买多个商品，吧多个商品放在一个list集合里面。关联起来
    @Transient  //在数据库对应的时候，把它忽略掉
    private List<OrderDetail> orderDetailList;


    //通过code获取一个枚举
    @JsonIgnore  //加上此注解以后，对象转为json格式，就会忽略这个方法
    public OrderStatesEnum getOrderStatesEnum(){
        //根据订单状态，和其相对应的枚举类的class对象，得到枚举对象
        return EnumUtil.getByCode(orderStatus,OrderStatesEnum.class);
    }

    //通过code获取一个枚举
    @JsonIgnore
    public PayStatusEnum getPayStatesEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
