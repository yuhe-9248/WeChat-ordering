package com.imooc.utils;

/**
 * @author yuhe
 * @date 2021/11/23 21:06
 */
public class MathUtil {
    private static final Double Money_Range = 0.01;


    /**
     * 比较两个金额是否相等
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean equals(Double d1,Double d2){
        double result = Math.abs(d1 - d2);

        if (result < Money_Range){
            return true;
        }else {
            return false;
        }
    }
}
