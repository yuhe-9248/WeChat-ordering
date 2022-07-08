package com.imooc.utils;

import com.imooc.enums.CodeEnum;

/**
 * @author yuhe
 * @date 2021/11/24 20:34
 *
 * 根据枚举的code和枚举类的class对象，获取指定code的message
 */
public class EnumUtil {
    public static <T extends CodeEnum>T getByCode(Integer code, Class<T> enumClass){
        for (T each : enumClass.getEnumConstants()){//getEnumConstants()返回此枚举类的元素，如果此Class对象不表示枚举类型，则返回null。
            if (code.equals(each.getCode())){
                return each;//如果和数字一样，返回这个枚举对象
            }
        }
        return null;
    }
}
