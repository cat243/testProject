package com.yupi.project.controller;


import com.fasterxml.jackson.databind.ser.Serializers;
import com.yupi.project.common.BaseResponse;
import com.yupi.project.common.ResultUtils;
import com.yupi.project.model.entity.Booking;
import com.yupi.project.service.BookingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Resource
    private BookingService bookingService;

    /**
     * 获取所有的订单信息
     * @return
     */
    @GetMapping("list")
    public BaseResponse<List<Booking>>ListAllOrders(){
        List<Booking> list = bookingService.list();
        if (list==null){
            list=new ArrayList<>();
        }
        return ResultUtils.success(list);
    }

    @GetMapping("/search")
    public BaseResponse<List<Booking>>SearchBooking(@RequestParam(required = false)String searchText){
        List<Booking> list = this.bookingService.lambdaQuery().like(Booking::getUsername, searchText).or()
                .like(Booking::getSeat_number, searchText).or()
                .like(Booking::getTime_slot, searchText).or()
                .like(Booking::getMovie_id, searchText).list();
        if(list==null){
            list=new ArrayList<>();
        }
        return ResultUtils.success(list);
    }


}
