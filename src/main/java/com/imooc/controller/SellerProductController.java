package com.imooc.controller;

import com.imooc.dataobject.ProductCategory;
import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.OrderDTO;
import com.imooc.exception.SellException;
import com.imooc.form.ProductForm;
import com.imooc.service.CategoryService;
import com.imooc.service.ProductService;
import com.imooc.service.ProductServiceImpl;
import com.imooc.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
 * 卖家端商品
 *
 * @author yuhe
 * @date 2021/11/25 22:16
 */

@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CategoryService categoryService;


    /**
     * 列表
     *
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "5") Integer size,
                             Map<String,Object> map){
        PageRequest request = PageRequest.of(page-1,size);
        Page<ProductInfo> productInfoPage = productService.findAll(request);

        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("currentSize",size);

        return new ModelAndView("product/list",map);
    }

    /**
     * 商品上架
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){

        try {
            productService.onSale(productId);
        } catch (SellException e) {
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    /**
     * 商品下架
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){

        try {
            productService.offSale(productId);
        } catch (SellException e) {
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    //
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId,
                      Map<String,Object> map) {
        //如果product不是空，就是传入了参数
        if (!StringUtils.isEmpty(productId)){
//        if (!StringUtils.hasText(productId)){
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo",productInfo);
        }
        //查询所有的类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);

        return new ModelAndView("product/index",map);


    }

    /**
     * 保存   /  更新
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("save")
//    @CachePut(cacheNames ="product",key = "123")//这个当修改时让缓存失效。但方法返回的是modelAndView对象，而实际监控应该是一个vo对象。
//    @CacheEvict(cacheNames ="product",key = "123")//方法访问之后，吧缓存清除掉
    public ModelAndView save(@Valid ProductForm form,//
                             BindingResult bindingResult,//验证结果
                             Map<String , Object> map){
        if (bindingResult.hasErrors()){ //所有字段是否验证通过，true-数据有误  false-数据无误
            map.put("msg",bindingResult.getFieldError().getDefaultMessage() );
            map.put("url","/sell/seller/product/index"); //出错跳到新增商品页
            return new ModelAndView("common/error",map);
        }
        ProductInfo productInfo = new ProductInfo();
        try {
            //根据productId是否为空，判断是新增还是数据库已经存在的。
            if (!StringUtils.isEmpty(form.getProductId())){
                //不为空，数据库有，则查询一下
                productInfo = productService.findOne(form.getProductId());//查询之后再拷贝，防止有些地方变为null
            }else {
                form.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(form,productInfo);
            productService.save(productInfo);
        } catch (SellException e) {
            map.put("msg",e.getMessage() );
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list"); //没问题跳到列表页
        return new ModelAndView("common/success",map);

    }

}
