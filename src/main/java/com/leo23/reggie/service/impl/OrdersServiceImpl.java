package com.leo23.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo23.reggie.common.BaseContext;
import com.leo23.reggie.common.CustomException;
import com.leo23.reggie.entity.*;
import com.leo23.reggie.mapper.OrdersMapper;
import com.leo23.reggie.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 订单表 服务实现类
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 用户下单
     *
     * @param orders
     */
    @Transactional
    @Override
    public void submit(Orders orders) {
        // 查看用户购物车数据
        LambdaQueryWrapper<ShoppingCart> shoppingCartWrapper = new LambdaQueryWrapper<>();
        shoppingCartWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(shoppingCartWrapper);
        if (shoppingCartList == null || shoppingCartList.size() == 0) {
            throw new CustomException("购物车为空，不能下单");
        }
        // 用户 地址
        User user = userService.getById(BaseContext.getCurrentId());
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());
        if (addressBook == null) {
            throw new CustomException("地址信息有误，不能下单");
        }

        // 计算总价格
        AtomicLong amount = new AtomicLong(0);

        // 订单插入数据
        long orderId = IdWorker.getId();
        List<OrderDetail> orderDetails = shoppingCartList.stream().map((item) -> {
            OrderDetail detail = new OrderDetail();
            detail.setOrderId(orderId);
            detail.setNumber(item.getNumber());
            detail.setDishId(item.getDishId());
            detail.setDishFlavor(item.getDishFlavor());
            detail.setSetmealId(item.getSetmealId());
            detail.setName(item.getName());
            detail.setImage(item.getImage());
            detail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return detail;
        }).collect(Collectors.toList());

        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setUserId(BaseContext.getCurrentId());
        orders.setNumber(String.valueOf(orderId));
        orders.setUsername(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));

        this.save(orders);

        // 订单明细插入多条数据
        orderDetailService.saveBatch(orderDetails);

        // 清空购物车
        shoppingCartService.remove(shoppingCartWrapper);

    }
}
