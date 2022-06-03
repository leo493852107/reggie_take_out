package com.leo23.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo23.reggie.common.CustomException;
import com.leo23.reggie.entity.Category;
import com.leo23.reggie.entity.Dish;
import com.leo23.reggie.entity.Setmeal;
import com.leo23.reggie.mapper.CategoryMapper;
import com.leo23.reggie.service.CategoryService;
import com.leo23.reggie.service.DishService;
import com.leo23.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    /**
     * 根据 id 删除分类
     *
     * @param id
     */
    @Override
    public void remove(Long id) {
        // 查询分类是否关联了`菜品`，如果已经关联，抛出异常
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        long count1 = dishService.count(dishLambdaQueryWrapper);
        if (count1 > 0) {
            throw new CustomException("当前分类已关联菜品，不能删除");
        }

        // 查询分类是否关联了`套餐`，如果已经关联，抛出异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        long count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0) {
            throw new CustomException("当前套餐已关联菜品，不能删除");
        }

        // 正常删除
        super.removeById(id);
    }

}
