package com.imooc.controller;

import com.imooc.config.ProjectUrlConfig;
import com.imooc.constant.CookieConstant;
import com.imooc.constant.RedisConstant;
import com.imooc.dataobject.SellerInfo;
import com.imooc.enums.ResultEnum;
import com.imooc.service.SellerService;
import com.imooc.service.SellerServiceImpl;
import com.imooc.utils.CookieUtil;
import com.mysql.jdbc.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author yuhe
 * @date 2021/11/28 14:26
 *
 * 卖家用户相关操作
 */

@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerServiceImpl sellerService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                      HttpServletResponse httpServletResponse,
                      Map<String,Object> map){
        //1、openid和数据库里面的做匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null){
            map.put("msg", ResultEnum.LOGIN_FALL);
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error");
        }
        //2、设置token至redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;  //过期时间
        /**
         * redisTemplate.opsForValue().set()  给redis添加一个值  使用set方式添加
         * String.format(RedisConstant.TOKEN_PREFIX,token)  第二个参数替换第一个参数里面的%s。k
         * openid   view
         * expire   过期时间
         * TimeUnit.SECONDS   时间格式
         */
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid ,expire, TimeUnit.SECONDS);

        //3、设置token至cookie
        CookieUtil.set(httpServletResponse, CookieConstant.TOKEN,token,expire);

        return new ModelAndView("redirect:" + projectUrlConfig.getSell() +"/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String,Object> map){
                //1、从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null){
        //2、清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue() ));
        //3、清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);//设置cookie为null 过期时间设置为0就等于取消了
        }

        map.put("msg",ResultEnum.LOGOOUT.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }


}
