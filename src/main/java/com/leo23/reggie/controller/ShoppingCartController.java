package com.leo23.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.leo23.reggie.common.BaseContext;
import com.leo23.reggie.common.R;
import com.leo23.reggie.entity.ShoppingCart;
import com.leo23.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 购物车
 */
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加到 购物车
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<Integer> add(@RequestBody ShoppingCart shoppingCart) {
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCart.setUserId(BaseContext.getCurrentId());
        int num = shoppingCartService.add(shoppingCart);
        return R.success(num);

//  public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
//        // 设置用户id，指定哪一个用户的购物车数据
//        Long currentId = BaseContext.getCurrentId();
//        shoppingCart.setUserId(currentId);
//
//        // 查询菜品是否在购物车中，如果存在 +1
//        Long dishId = shoppingCart.getDishId();
//        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(ShoppingCart::getUserId, currentId);
//        if (dishId != null) {
//            // 菜品
//            wrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
//        } else {
//            // 套餐
//            wrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
//        }
//        ShoppingCart cart = shoppingCartService.getOne(wrapper);
//        if (cart != null) {
//            // 已存在，数量+1
//            cart.setNumber(cart.getNumber() + 1);
//            shoppingCartService.updateById(cart);
//        } else {
//            // 不存在，设置数量为1
//            shoppingCart.setNumber(1);
//            shoppingCart.setCreateTime(LocalDateTime.now());
//            shoppingCartService.save(shoppingCart);
//            cart = shoppingCart;
//        }
//
//        return R.success(cart);
    }

    @PostMapping("/sub")
    public R<Integer> sub(@RequestBody ShoppingCart shoppingCart) {
        shoppingCart.setUserId(BaseContext.getCurrentId());
        int num = shoppingCartService.sub(shoppingCart);
        return R.success(num);
    }

    /**
     * 查看购物车
     *
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        wrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(wrapper);
        return R.success(list);
    }

    /**
     * 清空购物车
     *
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean() {

        //根据当前登录用户的id 删除它的购物车即可
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartService.remove(queryWrapper);

        return R.success("清空购物车");
    }
}
