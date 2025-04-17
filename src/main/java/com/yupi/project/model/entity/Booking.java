package com.yupi.project.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName booking
 */
@TableName(value ="booking")
@Data
public class Booking implements Serializable {
    /**
     * 
     */
    @TableId
    private String movie_id;

    /**
     * 
     */
    @TableField(value = "seat_number", insertStrategy = FieldStrategy.IGNORED)
    private String seat_number;

    /**
     * 
     */
    private String time_slot;

    /**
     * 
     */
    private String username;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}