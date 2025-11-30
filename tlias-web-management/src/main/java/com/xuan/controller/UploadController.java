package com.xuan.controller;

import com.xuan.pojo.Result;
import com.xuan.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class UploadController {
    /**
     * 本地磁盘存储方案
     */
    /*@PostMapping("/upload")
    public Result handleFileUpload(String name, Integer age, MultipartFile file) throws IOException {
        //获取日志
        log.info("文件上传:{}",file);
        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        //创建新的文件名
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + extension;

        //保存文件
        file.transferTo(new File("D:/images/"+newFileName));
        return Result.success();
    }*/

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        //输出日志
        log.info("文件上传：{}",file.getOriginalFilename());
        //将文件交给OSS存储管理
        String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
        log.info("文件上传到OSS，url:{}",url);
        return Result.success(url);
    }
}