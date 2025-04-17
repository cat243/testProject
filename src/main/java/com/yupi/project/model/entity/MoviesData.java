package com.yupi.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName movies_data
 */
@TableName(value ="movies_data")
@Data
public class MoviesData implements Serializable {
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
    private String image_path;

    /**
     * 电影描述
     */
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}