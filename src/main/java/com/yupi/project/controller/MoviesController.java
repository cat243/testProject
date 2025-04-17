package com.yupi.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.project.Utils.UploadImage;
import com.yupi.project.common.BaseResponse;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.common.ResultUtils;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.exception.ThrowUtils;
import com.yupi.project.model.entity.MoviesData;
import com.yupi.project.service.MoviesDataService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    @Resource
    private MoviesDataService moviesDataService;

    @Resource
    private UploadImage uploadImage;

    /**
     * 分页查询电影
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @GetMapping("/page")
    public IPage<MoviesData> ListMoviesByPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        Page<MoviesData> page = new Page<>(pageNum, pageSize);
        return moviesDataService.page(page);

    }

    @GetMapping("/list")
    public BaseResponse<List<MoviesData>>ListMoviesDate(){
        List<MoviesData> list = moviesDataService.list();
        if(list==null){
            return ResultUtils.success(new ArrayList<>());
        }
        return ResultUtils.success(list);
    }


    /**
     * 根据id删除电影
     */
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Boolean>deleteMovieById(@PathVariable Long id){
        if(id==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        MoviesData moviesData = moviesDataService.getById(id);
        if(moviesData==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"该电影不存在");
        }
        boolean result = moviesDataService.removeById(moviesData);
        if(!result){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除电影失败");
        }
        return ResultUtils.success(result);
    }

    /**
     * 添加电影
     */
    @PostMapping("/add")
    public BaseResponse<Integer>addMovies(@RequestParam String name, @RequestParam String description,
                                          @RequestPart("image") MultipartFile images) throws IOException {
        //1.校验参数
        ThrowUtils.throwIf(name==null,new BusinessException(ErrorCode.PARAMS_ERROR,"电影名字不能为空"));
        ThrowUtils.throwIf(description==null,new BusinessException(ErrorCode.PARAMS_ERROR,"电影描述不能为空"));
        ThrowUtils.throwIf(images==null,new BusinessException(ErrorCode.PARAMS_ERROR,"电影图片不能为空"));
        //2.使用oss将图片转化成url
        String ImagesUrl = uploadImage.uploadByOss(images);
        MoviesData moviesData=new MoviesData();
        moviesData.setName(name);
        moviesData.setImage_path(ImagesUrl);
        moviesData.setDescription(description);
        boolean save = moviesDataService.save(moviesData);
        if(!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"增加电影失败");
        }
        return ResultUtils.success(moviesData.getId());
    }


    /**
     * 添加电影
     */
    @PutMapping("/update")
    public BaseResponse<Boolean>updateMovies(@RequestParam Integer id,
                                          @RequestParam String name, @RequestParam String description,
                                          @RequestPart(value = "image",required = false)MultipartFile images) throws IOException {
        //1.校验参数
        ThrowUtils.throwIf(id==null,new BusinessException(ErrorCode.PARAMS_ERROR));
        ThrowUtils.throwIf(name==null,new BusinessException(ErrorCode.PARAMS_ERROR,"电影名字不能为空"));
        ThrowUtils.throwIf(description==null,new BusinessException(ErrorCode.PARAMS_ERROR,"电影描述不能为空"));
        //2.使用oss将图片转化成url
        MoviesData moviesData=moviesDataService.getById(id);
        moviesData.setName(name);
        moviesData.setDescription(description);
        if(images!=null){
            String ImagesUrl = uploadImage.uploadByOss(images);
            moviesData.setImage_path(ImagesUrl);
        }
        boolean update = moviesDataService.updateById(moviesData);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改电影失败");
        }
        return ResultUtils.success(update);
    }

}
