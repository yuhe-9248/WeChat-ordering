package com.imooc.repository;

import com.imooc.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author yuhe
 * @date 2021/11/17 16:23
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;
    private final String OPENID = "ew3euwhd7sjw9diwkq";

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123123777");
        orderMaster.setBuyerName("老杨");
        orderMaster.setBuyerPhone("19237562937");
        orderMaster.setBuyerAddress("科大时代广场，4楼图论科技");
        orderMaster.setBuyerOpenid("ew3euwhd7sjw9diwkq");
        orderMaster.setOrderAmount(new BigDecimal(2.5));


        OrderMaster master = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(master);
    }

    @Test
    public void findByBuyerOpenid() {
        PageRequest request = PageRequest.of(0,1);

        Page<OrderMaster> result = orderMasterRepository.findByBuyerOpenid(OPENID, request);
        System.out.println("获取总的数据"+result.getTotalElements());
    }
}