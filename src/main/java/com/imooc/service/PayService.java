package com.imooc.service;

import com.imooc.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

/**
 * 支付
 *
 * @author yuhe
 * @date 2021/11/22 18:02
 */
public interface PayService {

    //创建支付订单
    PayResponse create(OrderDTO orderDTO);

    //支付订单后，接收微信异步请求通知，用来修改订单的支付状态
    PayResponse notify(String notifyData);

    //退款
    RefundResponse refund(OrderDTO orderDTO);
}
