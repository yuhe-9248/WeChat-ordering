package com.imooc.utils;

import java.util.Random;

/**
 * @author yuhe
 * @date 2021/11/19 12:45
 */
public class KeyUtil {

    //生成唯一主键
    //格式：时间+随机数
    public static synchronized String genUniqueKey(){
        Random random = new Random();

        //生成6位随机数
        Integer number = random.nextInt(900000)+100000;

        return System.currentTimeMillis() + String.valueOf(number);

    }
}
