package com.leo23.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo23.reggie.dto.SetmealDto;
import com.leo23.reggie.entity.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时删除 套餐和菜品的 关联数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids);
}
