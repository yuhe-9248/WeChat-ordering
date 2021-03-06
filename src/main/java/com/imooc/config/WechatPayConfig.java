package com.imooc.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author yuhe
 * @date 2021/11/22 19:39
 */

@Component
public class WechatPayConfig {

    @Autowired
    private WechatAccountConfig wechatAccountConfig;


    @Bean
    public BestPayServiceImpl bestPayService(){
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(wechatAccountConfig.getMyAppId());
        wxPayH5Config.setAppSecret(wechatAccountConfig.getMyAppSecret());
        wxPayH5Config.setMchId(wechatAccountConfig.getMchId());
        wxPayH5Config.setMchKey(wechatAccountConfig.getMchKey());
        wxPayH5Config.setKeyPath(wechatAccountConfig.getKeyPath());
        wxPayH5Config.setNotifyUrl(wechatAccountConfig.getNotifyUrl());

        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config);
        return bestPayService;
    }
}
