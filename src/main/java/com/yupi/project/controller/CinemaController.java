package com.yupi.project.controller;


import com.yupi.project.common.BaseResponse;
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

}
