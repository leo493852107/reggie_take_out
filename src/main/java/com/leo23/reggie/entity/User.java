package com.leo23.reggie.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final Long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String phone;
    // 0 女 1 男
    private String sex;
    private String idNumber;
    private String avatar;
    // 0 禁用 1正常
    private Integer status;
}
