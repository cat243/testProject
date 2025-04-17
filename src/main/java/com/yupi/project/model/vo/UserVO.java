package com.yupi.project.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class UserVO implements Serializable {

    /**
     * userId
     */
    private Integer id;



    /**
     * 用户账号
     */
    private String username;

    /**
     * 名字
     */
    private String name;


    /**
     * 用户状态 1表示正常 0表示禁用
     */
    private int status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}