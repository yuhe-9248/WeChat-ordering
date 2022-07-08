package com.imooc.service;

import com.imooc.exception.SellException;
import com.imooc.utils.KeyUtil;
import org.omg.CORBA.TIMEOUT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuhe
 * @date 2021/11/30 22:37
 */
@Service
public class SecKillServiceImpl implements SecKillService {

    @Autowired
    private RedisLock redisLock;

    private static final int TIMEOUT = 10 * 1000;//超时时间

    /**
     * 国庆活动，皮蛋粥特价 限量100000份
     */
    static Map<String,Integer> products;
    static Map<String,Integer> stock;
    static Map<String,String> orders;
    static {
        /**
         * 模拟多个表，商品信息表，库存表，秒杀成功订单表
         */
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456",100000);
        stock.put("123456",100000);
    }

    private String queryMap(String productId){
        return "国庆活动，皮蛋瘦肉粥特价，限量份"
                +products.get(productId)
                +"还剩："+stock.get(productId)+"份。该商品成功下单用户数目："
                +orders.size() + "人";
    }

    //查询 还有多少库存
    @Override
    public String querySecKillProductInfo(String productId) {
        return this.queryMap(productId);
    }

    //秒杀核心方法
    @Override
    public void orderProductMockDiffUser(String productId) {
        //加锁
        long time = System.currentTimeMillis() + TIMEOUT;
        if (!redisLock.lock(productId,String.valueOf(time))){
            //如果加锁没成功，返回异常
             throw new SellException(101,"哎呦喂,人也太多了，换一个姿势再试一试~~");
        }

        //1、查询该商品库存，为0则活动结束
        int stockNum = stock.get(productId);
        if (stockNum == 0){
            throw new SellException(100,"活动结束");
        }else {
            //2、下单（模拟不同用户openid不同
            orders.put(KeyUtil.genUniqueKey(),productId);
            //3、减库存
            stockNum = stockNum - 1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock.put(productId,stockNum);
        }
        //解锁
        redisLock.unlock(productId,String.valueOf(time));
    }
}
