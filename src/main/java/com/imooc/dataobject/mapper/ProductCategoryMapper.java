package com.imooc.dataobject.mapper;

import com.imooc.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yuhe
 * @date 2021/11/30 11:15
 */
@Component
public interface ProductCategoryMapper {

    @Insert("insert into product_category(category_name,category_type) values (#{category_name,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER})")
    int insertByMap (Map<String,Object> map);


    @Insert("insert into product_category(category_name,category_type) values (#{categoryName,jdbcType=VARCHAR},#{categoryType,jdbcType=INTEGER})")
    int insertByObject (ProductCategory productCategory);

    @Select("select * from product_category where category_type = #{categoryType}")
    @Results({
            @Result(column = "category_id",property = "categoryId"),//因为返回的是ProductCategory类型的数据，
            @Result(column = "category_name",property = "categoryName"),//和数据库里面的名字不对应，这里就是为了
            @Result(column = "category_type",property = "categoryType")//让数据库和对象的字段对应起来
    })
    ProductCategory findByCategoryType(Integer categoryType);


    //根据类别修改名字
    @Update("update product_category set category_name = #{categoryName} where category_type=#{categoryType} ")
    int updateCategoryType(@Param("categoryName") String categoryName,
                           @Param("categoryType") Integer categoryType);

    //根据对象更新
    @Update("update product_category set category_name = #{categoryName} where category_type=#{categoryType} ")
    int updateByObject(ProductCategory productCategory);


    //根据type删除
    @Delete("delete from product_category where category_type=#{categoryType}")
    int deleteByCategoryType(Integer categoryType);



    ProductCategory selectByCategoryType(Integer categoryType);



}
