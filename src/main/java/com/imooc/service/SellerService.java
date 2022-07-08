package com.imooc.service;

import com.imooc.dataobject.SellerInfo;

/**
 * @author yuhe
 * @date 2021/11/27 21:48
 *
 * 卖家端service
 */
public interface SellerService {

    //通过openid查询卖家端信息
    SellerInfo findSellerInfoByOpenid(String openid);
}
