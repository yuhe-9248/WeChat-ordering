package com.imooc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yuhe
 * @date 2021/11/21 22:00
 */
@Component
@Data
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    //公众平台Id
    private String myAppId;

    //公众平台秘钥
    private String myAppSecret;

    //开放平台id
    private String openAppId;

    //开放平台秘钥
    private String openAppSecret;

    //商户号
    private String mchId;

    //商户秘钥
    private String mchKey;

    //商户证书
    private String keyPath;

    //微信支付异步通知地址
    private String notifyUrl;


    //微信模板id
    private Map<String,String> templateId;
}
