package com.imooc.dataobject.dao;

import com.imooc.dataobject.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author yuhe
 * @date 2021/11/30 12:42
 */
public class ProductCategoryDao {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    public int insertByMap(Map<String,Object> map){
        return productCategoryMapper.insertByMap(map);
    }




}
