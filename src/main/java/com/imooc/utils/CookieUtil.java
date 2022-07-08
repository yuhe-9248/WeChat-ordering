package com.imooc.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuhe
 * @date 2021/11/28 17:16
 */
public class CookieUtil {

    /**
     * 设置
     * @param response
     * @param name
     * @param vaLue
     * @param maxAge
     */
    public static void set(HttpServletResponse response,//
                           String name,
                           String vaLue,
                           int maxAge){
        Cookie cookie = new Cookie(name,vaLue);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 根据请求对象和name  来获取cookie
     * @param request
     * @param name
     * @return
     */
    public static Cookie get(HttpServletRequest request,//
                           String name){

        Map<String, Cookie> cookieMap = readCookieMap(request);//获取请求头里面所有的cookie集合
        if (cookieMap.containsKey(name)){//有一样的token就返回
            return cookieMap.get(name);
        }else {
            return null;
        }
    }
    //吧cookie数组里的cookie查出来加入到一个map集合里面
    private static Map<String,Cookie> readCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;

    }

}
