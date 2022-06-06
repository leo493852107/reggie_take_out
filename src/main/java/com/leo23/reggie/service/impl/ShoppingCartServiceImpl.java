package com.leo23.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo23.reggie.common.BaseContext;
import com.leo23.reggie.entity.ShoppingCart;
import com.leo23.reggie.mapper.ShoppingCartMapper;
import com.leo23.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    /**
     * 得去判断 先去看看 当前就你这个人 买的购物项涉及菜品也好 套餐也好 如果存在 加+1即可
     *
     * @param shoppingCart
     * @return
     */
    @Override
    public int add(ShoppingCart shoppingCart) {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, shoppingCart.getUserId());
        // 用户添加购物项 是菜品 或者 套餐
        wrapper.eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId());
        wrapper.eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        ShoppingCart cartFromDatabase = this.getOne(wrapper);
        if (cartFromDatabase != null) {
            cartFromDatabase.setNumber(cartFromDatabase.getNumber() + 1);
            this.updateById(cartFromDatabase);
            return cartFromDatabase.getNumber();
        }
        shoppingCart.setNumber(1);
        this.save(shoppingCart);
        return 1;
    }

    @Override
    public int sub(ShoppingCart shoppingCart) {
        //1.先去查询 该购物项 个数是?
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        // 用户减去购物项 是菜品
        wrapper.eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId());
        // 用户减去购物项 是套餐
        wrapper.eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        ShoppingCart cartFromDatabase = this.getOne(wrapper);
        if (cartFromDatabase.getNumber() > 1) {
            cartFromDatabase.setNumber(cartFromDatabase.getNumber() - 1);
            this.updateById(cartFromDatabase);
            return cartFromDatabase.getNumber();
        } else {
            this.removeById(cartFromDatabase.getId());
            return 0;
        }
    }
}
