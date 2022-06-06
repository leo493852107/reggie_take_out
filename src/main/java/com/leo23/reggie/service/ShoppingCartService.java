package com.leo23.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo23.reggie.entity.ShoppingCart;

public interface ShoppingCartService extends IService<ShoppingCart> {
    // 添加产品
    int add(ShoppingCart shoppingCart);
    // 减少产品
    int sub(ShoppingCart shoppingCart);
}
