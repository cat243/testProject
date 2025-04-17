package com.yupi.project.model.dto.cinema;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class cinemaVO {

    /**
     *影院名字
     */
    private String name;

    /**
     *影院地址
     */
    private String adress;


    /**
     *影院距离
     */
    private String distance;


    /**
     * 影院上架影片的信息
     */
    private List<MoviesVO>moviesVOS;
}
