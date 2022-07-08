package com.imooc.repository;

import com.imooc.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yuhe
 * @date 2021/11/17 16:41
 */


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void SaveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("124323");
        orderDetail.setOrderId("1233423");
        orderDetail.setProductId("9903432425");
        orderDetail.setProductIcon("http://xxxxx.jpg");
        orderDetail.setProductName("烤鸭");
        orderDetail.setProductPrice(new BigDecimal(78.6));
        orderDetail.setProductQuantity(2);


        OrderDetail save = orderDetailRepository.save(orderDetail);
        Assert.assertNotNull(save);

    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> byOrderId = orderDetailRepository.findByOrderId("1233423");
        Assert.assertNotEquals(0,byOrderId.size());
    }
}