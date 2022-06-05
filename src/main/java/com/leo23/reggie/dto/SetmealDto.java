package com.leo23.reggie.dto;

import com.leo23.reggie.entity.Setmeal;
import com.leo23.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {
    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
