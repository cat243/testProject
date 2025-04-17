package com.yupi.project.Utils;

import cn.hutool.core.io.FileUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.exception.ThrowUtils;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 本地上传图片
 */
@Service
public class UploadImage {

    //oss域名
    public static final String ALI_DOMAIN="https://rg-homework.oss-cn-guangzhou.aliyuncs.com/";
    /**
     * 本地保存上传的单个图片
     * @param file
     * @return 该图在本地上的绝对路径
     */
    public String uploadImageFile(MultipartFile file) throws IOException {
        //效验
        if(file.isEmpty()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"图片为空");
        }
        String originalFilename = file.getOriginalFilename();
        //判断后缀是否合法
        String suffix = FileUtil.getSuffix(originalFilename);
        final List<String> validFileSuffix= Arrays.asList("jpg","jpeg","svg","png");//文件后缀的白名单
        ThrowUtils.throwIf(!validFileSuffix.contains(suffix),ErrorCode.PARAMS_ERROR,"文件后缀非法");
        //file 重命名，避免用户上传同样的图片后图片覆盖
        String uuid= UUID.randomUUID().toString().replace("-","");
        String newFileName=uuid+"."+suffix;
        //上传图片
        ApplicationHome applicationHome=new ApplicationHome(this.getClass());
        String pre= applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() +
                "\\src\\main\\resources\\images\\";
        String path=pre+newFileName;
        file.transferTo(new File(path));
        return path;

    }


    /**
     * 采用oss来存储上传的图片文件
     */
    public String uploadByOss(MultipartFile file) throws IOException {
        //生成文件名
        String originalFilename = file.getOriginalFilename();//原来的文件名
        String suffix = FileUtil.getSuffix(originalFilename);//后缀
        String uuid= UUID.randomUUID().toString().replace("-","");
        String newFileName=uuid+"."+suffix;
        //地域节点
        String endpoint="http://oss-cn-guangzhou.aliyuncs.com";
        String accessKeyId="xxxxxxxxxxxxxxxxxxxxxxxx";
        String accessKeySecret="xxxxxxxxxxxxxxxxxxxxxx";
        //OSS客户端对象
        OSS ossClient=new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        ossClient.putObject(
                "rg-homework",//仓库名
                newFileName,//文件名
                file.getInputStream()
        );
        ossClient.shutdown();//关闭oss流
        return ALI_DOMAIN+newFileName;
    }



}
