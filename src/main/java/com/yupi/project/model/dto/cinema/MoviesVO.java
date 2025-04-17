package com.yupi.project.model.dto.cinema;

import lombok.Data;

import java.util.Date;
@Data
public class MoviesVO {
//   "movie_name": "电影1",
//   "release_date": "2024-12-01"
//    price;

    /**
     * 电影名称
     */
    String movie_name;

    /**
     * 上映时间
     */
    Date release_date;

    /**
     * 价格
     */
    int  price;

}
