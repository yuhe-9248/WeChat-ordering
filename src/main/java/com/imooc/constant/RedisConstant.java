package com.imooc.constant;

/**
 * @author yuhe
 * @date 2021/11/28 16:50
 *
 * redis常量
 */
public interface RedisConstant {

    String TOKEN_PREFIX = "token_%s";//前缀

    Integer EXPIRE = 7200;//两小时
}
