package com.imooc.repository;

import com.imooc.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yuhe
 * @date 2021/11/14 22:19
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest(){
        ProductCategory productCategory = repository.getOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    @Transactional 
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory("女生最爱",3);
        ProductCategory save = repository.save(productCategory);
        Assert.assertNotNull(save);//断言 判断save不为空

//        ProductCategory productCategory = repository.findById(2).get();
//        productCategory.setCategoryType(3);
//
//        repository.save(productCategory);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(2,3,4);

        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result);//断言result  不等于0

    }


}