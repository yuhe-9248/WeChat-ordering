package com.imooc.service;

import com.imooc.converter.OrderMaster2OrderDTOConverter;
import com.imooc.dataobject.OrderDetail;
import com.imooc.dataobject.OrderMaster;
import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDTO;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.OrderStatesEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.ResponseBankException;
import com.imooc.exception.SellException;
import com.imooc.repository.OrderDetailRepository;
import com.imooc.repository.OrderMasterRepository;
import com.imooc.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.beans.Transient;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yuhe
 * @date 2021/11/19 11:58
 *
 * 订单
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private PayServiceImpl payService;
    @Autowired
    private PushMessageImpl pushMessage;
    @Autowired
    private WebSocket webSocket;



    //总价
    private BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);


    //创建订单
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        //订单id
        String orderId = KeyUtil.genUniqueKey();

        //1、查询商品（数量，价格）。防止库存不够，前端价格与数据库价格不匹配问题
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()){//遍历订单表中的订单详情（每一个菜）
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());//根据订单详情的id，查询商品。

            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
//                throw new ResponseBankException();
            }
            //2、计算订单总价
            orderAmount = productInfo.getProductPrice()//价格
                    .multiply(new BigDecimal( orderDetail.getProductQuantity()))//乘以 数量
                    .add(orderAmount);//加上原来的

            //订单详情入库
            BeanUtils.copyProperties(productInfo,orderDetail);//吧商品的属性拷贝到商品详情
            orderDetail.setDetailId(KeyUtil.genUniqueKey());//订单详情页的主键id
            orderDetail.setOrderId(orderId);//订单id。在创建订单的时候就生成了
            orderDetailRepository.save(orderDetail);//吧订单详情入数据库
        }

        //3、写入订单数据库 （OrderMaster和OrderDetail）
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);//订单号
        BeanUtils.copyProperties(orderDTO,orderMaster);//吧传入的数据，拷贝到orderMaster里面
        orderMaster.setOrderAmount(orderAmount);//总价
        //Master表里面订单状态和字符状态 我们设置为默认值，但一经过拷贝，就把dto里面的null拷贝给了master里面进行覆盖
        orderMaster.setOrderStatus(OrderStatesEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterRepository.save(orderMaster);//入库

        //4、下单成功，扣库存
        List<CartDTO> cartDTOList = new ArrayList<>();
        cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());

        productService.decreaseStock(cartDTOList);

        //发生websocket消息
        webSocket.sendMessage(orderDTO.getOrderId());

        return orderDTO;
    }

    //查询单个订单
    @Override
    @Transactional
    public OrderDTO findOne(String orderId) {
        //根据订单id，查询到一个订单
        OrderMaster orderMaster = orderMasterRepository.getById(orderId);
        if (orderMaster == null){//订单不存在，抛出订单不存在异常
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);//根据订单id，查询订单详情
        if (CollectionUtils.isEmpty(orderDetailList)){//订单详情不存在，抛出订单详情不存在异常
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);//设置订单详情

        return orderDTO;
    }

    //查询订单列表
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMastersPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMastersPage.getContent());

        return  new PageImpl<OrderDTO>(orderDTOList,pageable,orderMastersPage.getTotalElements());

    }

    //取消订单
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatesEnum.NEW.getCode())){//如果DTO对象的支付状态不是新建状态
            log.error("取消订单，订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatesEnum.CANCEL.getCode());//订单状态修改为取消状态
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);//orderDTO对象复制给orderMaster对象
        int i = 0;
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);//取消的订单入数据库
        if (updateResult == null){
            log.error("取消订单，更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){//如果订单点餐处为空
            log.error("取消订单，订单中无商品详情，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        //购物车
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //如果已支付。需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //DOTO
//            payService.refund(orderDTO);//微信退款，跟微信交互有问题

        }
        return orderDTO;
    }

    //完结订单
    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatesEnum.NEW.getCode())){//如果订单不是新建状态，抛异常
            log.error("【完结订单】订单状态不正确，orderID={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatesEnum.FINISHED.getCode());//dto对象订单状态改为已完结
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null){
            log.error("完结订单，更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //推送微信模板消息
//        pushMessage.orderStatus(orderDTO);
        return orderDTO;
    }

    //支付订单
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatesEnum.NEW.getCode())){//如果订单不是新建状态，抛异常
            log.error("【支付订单】订单状态不正确，orderID={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){//如果不是待支付状态
            log.error("[订单支付]订单支付状态不正确,orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);

        }
        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());//dto对象支付状态改为已支付
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null){
            log.error("[订单支付]，支付失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        //convert(orderMasterPage.getContent());根据分页对象，查询到当前页对象的集合。  然后把master对象转为orderDTO对象

        //再把orderDTOList封装回去，变成page对象
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }
}
