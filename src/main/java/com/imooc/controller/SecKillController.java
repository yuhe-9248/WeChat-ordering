package com.imooc.controller;

import com.imooc.service.SecKillServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuhe
 * @date 2021/11/30 22:21
 *
 * 秒杀系统
 */
@RestController
@RequestMapping("/skill")
@Slf4j
public class SecKillController {

    @Autowired
    private SecKillServiceImpl seckillService;

    /**
     * 查询秒杀活动特价商品信息
     * @param productId
     * @return
     * @throws Exception
     */
    @GetMapping("/query/{productId}")
    public String query(@PathVariable String productId) throws Exception{
        //查询 并 返回  剩余库存
        return seckillService.querySecKillProductInfo(productId);
    }


    /**
     * 秒杀，，没有抢到获得 “哎呦喂 XXXX",抢到了会返回剩余的库存量
     * @param productId
     * @return
     * @throws Exception
     */
    @GetMapping("/order/{productId}")
    public String skill(@PathVariable String productId) throws Exception{
        log.info("@skill request,productId:"+productId);
        //进行秒杀
        seckillService.orderProductMockDiffUser(productId);
        //查询 并 返回 剩余库存
        return seckillService.querySecKillProductInfo(productId);
    }







}
