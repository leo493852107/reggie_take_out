package com.leo23.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo23.reggie.entity.Orders;

/**
 * 订单表 服务类
 */
public interface OrdersService extends IService<Orders> {
    public void submit(Orders orders);
}
