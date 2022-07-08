package com.imooc.service;

import com.imooc.dto.OrderDTO;

/**
 * @author yuhe
 * @date 2021/11/20 21:46
 *
 * 卖家
 */
public interface BuyerService {

    //查询一个订单
    OrderDTO findOrderOne(String openid,String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid,String orderId);

}
