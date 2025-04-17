package com.yupi.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName cinema
 */
@TableName(value ="cinema")
@Data
public class Cinema implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String adress;

    /**
     * 
     */
    private Integer price;

    /**
     * 
     */
    private String distance;

    /**
     * 
     */
    private Date available_date;

    /**
     * 
     */
    private Integer movieid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}