package com.imooc.controller;

import com.imooc.VO.ProductInfoVO;
import com.imooc.VO.ProductVO;
import com.imooc.VO.ResultVO;
import com.imooc.dataobject.ProductCategory;
import com.imooc.dataobject.ProductInfo;
import com.imooc.service.CategoryService;
import com.imooc.service.ProductService;
import com.imooc.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yuhe
 * @date 2021/11/16 22:02
 *
 * 买家商品
 */

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
//    @Cacheable(cacheNames = "product",key = "123")//加入到redis缓存
    public ResultVO list(){

        //1、查询所有的上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2、查询类目(一次性查询）
//        List<Integer> categoryTypeList = new ArrayList<>();
        //传统方法
//        for (ProductInfo productInfo : productInfoList){
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        //精简做法
        List<Integer> categoryTypeList/*根据上架商品查找所有类目*/ = productInfoList.stream()
                .map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList/*根据类目编号查找类目对象，放入list集合*/ = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3、数据拼装
        List<ProductVO> productVOList = new ArrayList<>();//商品包含类目集合
        //类目表                                  所有类目集合
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());//商品包含类目对象vo 设置类目类型
            productVO.setCategoryName(productCategory.getCategoryName());//商品包含类目对象vo 设置类目名字

            List<ProductInfoVO> productInfoVOList =new ArrayList<>();//
            //类目里面包含商品               所有商品集合
            for (ProductInfo productInfo : productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){//判断商品和类目一不一样
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);//将productInfo的值拷贝到productInfoVO对象里面
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);//商品包含类目对象vo添加 商品

            productVOList.add(productVO);
        }


//        ResultVO resultVO = new ResultVO();
//        resultVO.setCode(0);
//        resultVO.setMsg("成功");
//        resultVO.setData(productVOList);//类目  类目里面有商品
//        return resultVO;

        //使用一个工具类，完成上面5行工作
        return ResultVOUtil.success(productVOList);

    }

}
