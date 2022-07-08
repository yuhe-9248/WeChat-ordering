package com.imooc.service;

import com.imooc.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yuhe
 * @date 2021/11/16 18:58
 */

@RunWith(SpringRunner.class)  //实例@Autowired注入的类到容器中。如果不写会抛出NullPointerException异常
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;


    @Test
    public void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategoryList = categoryService.findAll();
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(Arrays.asList(1,2,3,4));
        for (ProductCategory productCategory : productCategoryList){
            System.out.println(productCategory.getCategoryName());
        }
        Assert.assertNotEquals(0,productCategoryList.size());

    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory("男生专享",10);
        ProductCategory save = categoryService.save(productCategory);
        Assert.assertNotNull(save);
    }
}