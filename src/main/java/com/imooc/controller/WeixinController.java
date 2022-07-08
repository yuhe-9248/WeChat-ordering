package com.imooc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author yuhe
 * @date 2021/11/21 15:41
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

//
    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){
        log.info("进入auth方法");
        log.info("[code]:{}",code);

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxc8f136928e57296b&secret=a5e99fc1b703ced12ef4c65e9f6515e4&code="+ code + "&grant_type=authorization_code";

        System.out.println("1");
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("response:{}",response);
    }

//    @GetMapping("/auth")
//    public void auth() {
//        log.info("进入auth方法");
//    }
}