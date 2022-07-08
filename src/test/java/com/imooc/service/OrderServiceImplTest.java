package com.imooc.service;

import com.imooc.dataobject.OrderDetail;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.OrderStatesEnum;
import com.imooc.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yuhe
 * @date 2021/11/19 14:14
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID ="ew3euwhd7sjw9diwkq";
    private final String ORDER_ID = "1637824734353870908";

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("科大时代广场4楼，图论科技");
        orderDTO.setBuyerName("宇鹤");
        orderDTO.setBuyerPhone("19763735372");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("123");//已经存在于数据库的商品id
        o1.setProductQuantity(1);//数量

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("123456");
        o2.setProductQuantity(1);

        OrderDetail o3 = new OrderDetail();
        o3.setProductId("123457");
        o3.setProductQuantity(1);

        OrderDetail o4 = new OrderDetail();
        o4.setProductId("66666");
        o4.setProductQuantity(1);

        orderDetailList.add(o1);
        orderDetailList.add(o2);
        orderDetailList.add(o3);
//        orderDetailList.add(o4);

        orderDTO.setOrderDetailList(orderDetailList);//购物车
        OrderDTO result = orderService.create(orderDTO);

        log.info("[创建订单] result={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO result = orderService.findOne(ORDER_ID);
        log.info("[查询单个订单]：result:{}",result);
//        Assert.assertNotEquals(ORDER_ID,result.getOrderId());//断言查询到的单个订单，和输入的订单一不一致。
    }

    @Test
    public void findList() {
        PageRequest request = PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, request);
//        Assert.assertEquals(,orderDTOPage.getTotalPages());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);//取消订单
        Assert.assertEquals(OrderStatesEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);//完成订单
        Assert.assertEquals(OrderStatesEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);//支付订单
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }


    @Test
    public void list(){
        PageRequest request = PageRequest.of(0,2);

        orderService.findList(request);

    }

}