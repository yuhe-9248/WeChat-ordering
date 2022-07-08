package com.imooc.dataobject.mapper;

import com.imooc.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author yuhe
 * @date 2021/11/30 11:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//websocket是需要依赖tomcat等容器的启动。所以在测试过程中我们要真正的启动一个tomcat作为容器。
//如果不加后面这一部分，在进行下面测试类时会报错
@Slf4j
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void insertByMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("category_name","师兄最爱");
        map.put("category_type","101");
        int result = mapper.insertByMap(map);
        Assert.assertEquals(1,result);
    }

    @Test
    public void insertByObject(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("进门必点");
        productCategory.setCategoryType(102);
        int result = mapper.insertByObject(productCategory);
        Assert.assertEquals(1,result);
    }

    @Test
    public void findByCategoryType(){
        ProductCategory result = mapper.findByCategoryType(101);
        Assert.assertNotNull(result);
    }

    @Test
    public void updateByCategoryType(){
        mapper.updateCategoryType("师兄最不爱",101);
    }
    @Test
    public void updateByObject(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("学姐最爱");
        productCategory.setCategoryType(101);
        int result = mapper.updateByObject(productCategory);
        Assert.assertEquals(1,result);
    }


    @Test
    public void deleteByCategoryType(){
        int result = mapper.deleteByCategoryType(102);
        Assert.assertEquals(1,result);
    }


    @Test
    public void selectByCategoryType(){
        mapper.selectByCategoryType(101);
    }









}