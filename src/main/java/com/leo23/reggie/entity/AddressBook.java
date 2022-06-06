package com.leo23.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AddressBook implements Serializable {
    private static final Long serialVersionUID = 1L;
    private Long id;
    private Long userId;
    // 收货人
    private String consignee;
    private String phone;
    // 0 女人 1 男人
    private String sex;
    // 省级区划分号
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String districtCode;
    private String districtName;
    // 详细地址
    private String detail;
    // 标签
    private String label;
    private Integer isDefault;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    // 创建人
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    // 修改人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    // 是否删除
    private Integer isDeleted;
}
