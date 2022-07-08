package com.imooc.controller;

import com.imooc.dataobject.ProductCategory;
import com.imooc.exception.SellException;
import com.imooc.form.CategoryForm;
import com.imooc.service.CategoryService;
import com.imooc.service.CategoryServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author yuhe
 * @date 2021/11/26 18:43
 *
 * 卖家类目
 */

@Controller
@RequestMapping("/seller/category")
public class SellerCateController {

    @Autowired
    private CategoryServiceImpl categoryService;


    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);

        return new ModelAndView("category/list",map);

    }

    /**
     * 展示类目表
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false) Integer categoryId,
                              Map<String,Object> map){
        if (categoryId != null){
            ProductCategory productCategory= categoryService.findOne(categoryId);
            map.put("productCategory",productCategory);
        }
        return new ModelAndView("category/index",map);
    }


    /**
     * 保存   更新
     * @param categoryForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage() );
            map.put("url","/sell/seller/category/index"); //出错跳到新增商品页
            return new ModelAndView("common/error",map);
        }
        ProductCategory productCategory = new ProductCategory();
        try {
            if (categoryForm.getCategoryId() != null){ //如果id不为空，再去数据库查数据
                productCategory = categoryService.findOne(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm,productCategory);
            //因为id是int型，而且是自增的，所以此处不用设置id
            categoryService.save(productCategory);
        } catch (SellException e) {
            map.put("msg",e.getMessage() );
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);
    }






}
