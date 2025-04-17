package com.yupi.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态(1表示正常  0表示禁用)
     */
    private Integer status;

    /**
     * 名字
     */
    private String name;

    /**
     * 用户权限，有user或者admin
     */
    private String role;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}