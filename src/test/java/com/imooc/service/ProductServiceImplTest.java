package com.imooc.service;

import com.imooc.dataobject.ProductInfo;
import com.imooc.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yuhe
 * @date 2021/11/16 21:43
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;


    @Test
    public void findOne() {
        ProductInfo one = productService.findOne("123456");
        Assert.assertEquals("123456",one.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> upAll = productService.findUpAll();
        Assert.assertNotEquals(0,upAll);
    }

//    @Test
//    public void findAll() {
////        Pageable pageable = new PageRequest(0,2);
////        Pageable pageable = new PageRequest();
//
//        Page<ProductInfo> productInfoPage = productService.findAll(pageable);
//        System.out.println(productInfoPage.getTotalElements());
//
//    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");//主键 id
        productInfo.setProductName("皮皮虾");//名字
        productInfo.setProductPrice(new BigDecimal(3.2));//价格
        productInfo.setProductStock(100);//库存
        productInfo.setProductDescription("很好吃的虾");//描述
        productInfo.setProductIcon("http://xxxxxxx.jpg");//小图
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());//状态
        productInfo.setCategoryType(2);//类目编号  2热销榜

        ProductInfo save = productService.save(productInfo);
        Assert.assertNotNull(save);
    }


    //测试上架
    @Test
    public void onSale(){
        ProductInfo productInfo = productService.onSale("123");

        Assert.assertEquals(ProductStatusEnum.UP,productInfo.getProductStatusEnum());
    }
    //测试上架
    @Test
    public void offSale(){
        ProductInfo productInfo = productService.offSale("123");

        Assert.assertEquals(ProductStatusEnum.DOWN,productInfo.getProductStatusEnum());
    }





}