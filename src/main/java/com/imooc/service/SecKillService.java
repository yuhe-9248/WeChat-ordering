package com.imooc.service;

/**
 * @author yuhe
 * @date 2021/11/30 22:36
 */
public interface SecKillService {

    String querySecKillProductInfo(String productId);

    void orderProductMockDiffUser(String productId);
}
