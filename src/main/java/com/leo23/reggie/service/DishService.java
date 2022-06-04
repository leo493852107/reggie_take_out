package com.leo23.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo23.reggie.dto.DishDto;
import com.leo23.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    // 新增菜品，同时插入口味数据 dish, dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    // 根据id查询菜品信息和口味信息
    public DishDto getByIdWithFlavor(Long id);

    // 更新菜品和口味信息
    public void updateWithFlavor(DishDto dishDto);
}
