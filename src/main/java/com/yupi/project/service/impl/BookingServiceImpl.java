package com.yupi.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.project.model.entity.Booking;
import com.yupi.project.service.BookingService;
import com.yupi.project.mapper.BookingMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【booking】的数据库操作Service实现
* @createDate 2024-12-13 21:08:07
*/
@Service
public class BookingServiceImpl extends ServiceImpl<BookingMapper, Booking>
    implements BookingService{

}




