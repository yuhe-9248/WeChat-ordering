package com.imooc.service;

import com.imooc.config.WechatAccountConfig;
import com.imooc.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author yuhe
 * @date 2021/11/29 13:05
 */

@Service
@Slf4j
public class PushMessageImpl implements PushMessage {

    @Autowired
    private WxMpService wxMpService;//公共平台service
    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    //订单状态变更消息
    @Override
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(wechatAccountConfig.getTemplateId().get("orderStatus"));
        templateMessage.setToUser(orderDTO.getBuyerOpenid());
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","亲，请记得收货。"),
                new WxMpTemplateData("keyword1","宇鹤微信点餐"),
                new WxMpTemplateData("keyword2","123054609587"),
                new WxMpTemplateData("keyword3",orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4",orderDTO.getOrderStatesEnum().getMessage()),
                new WxMpTemplateData("keyword5","￥"+orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark","欢迎再次光临：")
        );
        templateMessage.setData(data);
        try {
            /*
            wxMpService.getTemplateMsgService()  公共平台service获取关于消息通知的消息
            sendTemplateMsg(templateMessage); 发送消息
             */
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模板消息】发送失败，{}",e);

        }
    }
}
