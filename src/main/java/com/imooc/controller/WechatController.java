package com.imooc.controller;

import com.imooc.config.ProjectUrlConfig;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author yuhe
 * @date 2021/11/21 21:39
 */

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;


    //微信授权
    @RequestMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        //1、配置
        //2、调用方法
        String url = projectUrlConfig.getWechatMpAuthorize() + "/sell/wechat/userInfo";
        String redirectUrl = null;
        try {
            redirectUrl = wxMpService.getOAuth2Service().buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        log.info("网页授权获取code,result:{}",resultUrl);

        return "redirect:"+redirectUrl;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,//通过code获取openid
                         @RequestParam("state") String returnUrl) {
        WxOAuth2AccessToken wxOAuth2AccessToken;
        try {
            wxOAuth2AccessToken = wxMpService.getOAuth2Service().getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("微信网页授权：{}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxOAuth2AccessToken.getOpenId();
        return "redirect:"+returnUrl + "?openid=" + openId;

    }


    @GetMapping("/qrAuthorize")
    public String qrAuthorized(@RequestParam("returnUrl") String returnUrl){
        String url =  projectUrlConfig.wechatOpenAuthorized + "/sell/wechat/qrUserInfo";
//        String redirectUrl = wxOpenService.buildQrConnectUrl(url,WxConsts.QrConnectScope.SNSAPI_LOGIN,URLEncoder.encode(returnUrl));
        String redirectUrl = null;
        try {
            redirectUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.QrConnectScope.SNSAPI_LOGIN, URLEncoder.encode(returnUrl,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:"+redirectUrl;
    }
    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code,//通过code获取openid
                             @RequestParam("state") String returnUrl){
        WxOAuth2AccessToken wxOAuth2AccessToken = new WxOAuth2AccessToken();
        try {
            wxOAuth2AccessToken = wxOpenService.getOAuth2Service().getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("微信网页授权：{}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        log.info("wxOAuth2AccessToken:{}",wxOAuth2AccessToken);
        String openId = wxOAuth2AccessToken.getOpenId();
        return "redirect:"+returnUrl + "?openid=" + openId;
    }


}
