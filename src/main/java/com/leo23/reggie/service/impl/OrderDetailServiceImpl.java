package com.leo23.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo23.reggie.entity.OrderDetail;
import com.leo23.reggie.mapper.OrderDetailMapper;
import com.leo23.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * 订单明细表 服务实现类
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
    
}
