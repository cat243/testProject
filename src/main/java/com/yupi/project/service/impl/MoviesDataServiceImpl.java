package com.yupi.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.project.model.entity.MoviesData;
import com.yupi.project.service.MoviesDataService;
import com.yupi.project.mapper.MoviesDataMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【movies_data】的数据库操作Service实现
* @createDate 2024-12-12 01:45:14
*/
@Service
public class MoviesDataServiceImpl extends ServiceImpl<MoviesDataMapper, MoviesData>
    implements MoviesDataService{

}




