package com.imooc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yuhe
 * @date 2021/11/28 0:03
 */

@Data
@ConfigurationProperties(prefix = "projecturl")
@Component
public class ProjectUrlConfig {

    //微信公众账号授权url
    public String wechatMpAuthorize;

    //微信开发平台授权url
    public String wechatOpenAuthorized;

    //点餐系统
    public String sell;


    


}
