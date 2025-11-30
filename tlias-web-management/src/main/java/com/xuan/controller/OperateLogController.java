package com.xuan.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuan.pojo.OperateLog;
import com.xuan.pojo.OperateQueryParam;
import com.xuan.pojo.PageResult;
import com.xuan.pojo.Result;
import com.xuan.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/log")
public class OperateLogController {
    @Autowired
    private OperateLogService operateLogService;

    //对当前操作员工的操作日志进行分页查询
    @GetMapping("/page")
    public Result page(OperateQueryParam queryParam){
        log.info("分页查询操作日志: page={}, pageSize={}", queryParam.getPage(), queryParam.getPageSize());
        
        // 使用PageHelper进行分页
        PageHelper.startPage(queryParam.getPage(), queryParam.getPageSize());
        PageInfo<OperateLog> pageInfo = operateLogService.page(queryParam);
        
        // 封装分页结果
        PageResult pageResult = new PageResult(pageInfo.getTotal(), pageInfo.getList());
        return Result.success(pageResult);
    }
}