package com.imooc.service;

import com.imooc.dto.OrderDTO;

/**
 * @author yuhe
 * @date 2021/11/29 13:03
 *
 * 消息推送
 */
public interface PushMessage {

    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);

}
