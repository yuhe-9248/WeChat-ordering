package com.imooc.repository;

import com.imooc.dataobject.ProductInfo;
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
 * @date 2021/11/16 20:55
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");//主键 id
        productInfo.setProductName("皮蛋粥");//名字
        productInfo.setProductPrice(new BigDecimal(3.2));//价格
        productInfo.setProductStock(100);//库存
        productInfo.setProductDescription("很好喝的粥");//描述
        productInfo.setProductIcon("http://xxxxxxx.jpg");//小图
        productInfo.setProductStatus(0);//状态
        productInfo.setCategoryType(2);//类目编号  2热销榜


        ProductInfo result = productInfoRepository.save(productInfo);
        Assert.assertNotNull(result);


    }

    //查询上架的商品
    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfoList = productInfoRepository.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfoList.size());
    }
}