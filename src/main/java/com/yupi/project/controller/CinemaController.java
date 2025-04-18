package com.yupi.project.controller;


import com.yupi.project.common.BaseResponse;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.common.ResultUtils;
import com.yupi.project.model.dto.cinema.MoviesVO;
import com.yupi.project.model.dto.cinema.cinemaVO;
import com.yupi.project.model.entity.Cinema;
import com.yupi.project.model.entity.MoviesData;
import com.yupi.project.service.CinemaService;
import com.yupi.project.service.MoviesDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cinema")
public class CinemaController {

    @Resource
    private CinemaService cinemaService;

    @Resource
    private MoviesDataService moviesDataService;

    /**
     * 获取所有的影院数据
     * @return
     */
    @GetMapping("/list")
    BaseResponse<Set<cinemaVO>>listCinema(){
        List<Cinema> list = this.cinemaService.list();
        Set<cinemaVO>cinemaVOS=new HashSet<>();
        for (Cinema cinema : list) {
            cinemaVO vo=new cinemaVO();
            BeanUtils.copyProperties(cinema,vo);
            vo.setMoviesVOS(listMoviesInfoByCinemaName(cinema.getName()));
            cinemaVOS.add(vo);
        }
        return ResultUtils.success(cinemaVOS);
    }


    /**
     * 根据影院名字获取影院上架电影信息
     * @param cinemaName
     * @return
     */
    private List<MoviesVO> listMoviesInfoByCinemaName(String cinemaName){
        //获取一个影院所有上映的电影的id
        List<Cinema> list = this.cinemaService.lambdaQuery().eq(Cinema::getName, cinemaName).list();
        List<MoviesVO>vos=new ArrayList<>();
        for (Cinema cinema : list) {
            MoviesVO vo=new MoviesVO();
            vo.setRelease_date(cinema.getAvailable_date());
            vo.setPrice(cinema.getPrice());
            vo.setMovie_name(moviesDataService.getById(cinema.getMovieid()).getName());
            vos.add(vo);
        }
        return vos;
    }



    /**
     * 根据影院 ID 获取影院详细信息
     * @param cinemaId 影院 ID
     * @return 影院详细信息
     */
    @GetMapping("/detail")
    BaseResponse<cinemaVO> getCinemaById(@RequestParam("cinemaId") Long cinemaId){
        Cinema cinema = this.cinemaService.getById(cinemaId);
        if (cinema == null) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR,"影院未找到");
        }
        cinemaVO vo = new cinemaVO();
        BeanUtils.copyProperties(cinema, vo);
        vo.setMoviesVOS(listMoviesInfoByCinemaName(cinema.getName()));
        return ResultUtils.success(vo);
    }



    /**
     * 获取所有影院数据，支持分页
     * @param page 页码
     * @param size 每页条数
     * @return 影院列表
     */
    @GetMapping("/listPaged")
    BaseResponse<List<cinemaVO>> listCinemaPaged(@RequestParam("page") int page, @RequestParam("size") int size) {
        List<Cinema> list = this.cinemaService.lambdaQuery()
                .orderByAsc(Cinema::getId)  // 按照 ID 排序
                .last("LIMIT " + (page - 1) * size + "," + size)
                .list();

        if (list.isEmpty()) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR,"未找到影院信息");
        }

        List<cinemaVO> cinemaVOS = list.stream()
                .map(cinema -> {
                    cinemaVO vo = new cinemaVO();
                    BeanUtils.copyProperties(cinema, vo);
                    vo.setMoviesVOS(listMoviesInfoByCinemaName(cinema.getName()));
                    return vo;
                })
                .collect(Collectors.toList());

        return ResultUtils.success(cinemaVOS);
    }


    /**
     * 删除影院
     * @param cinemaId 影院 ID
     * @return 删除结果
     */
    @GetMapping("/delete")
    BaseResponse<String> deleteCinema(@RequestParam("cinemaId") Long cinemaId) {
        boolean removed = this.cinemaService.removeById(cinemaId);
        if (!removed) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR,"删除失败，未找到影院");
        }
        return ResultUtils.success("影院删除成功");
    }




}
