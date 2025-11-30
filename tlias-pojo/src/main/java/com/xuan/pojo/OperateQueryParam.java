package com.xuan.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class OperateQueryParam {
    private Integer page = 1; // 页码
    private Integer pageSize = 10; // 每页显示数量
    private String className; // 操作类名
    private String methodName; // 操作方法名
    private Integer operateEmpId; // 操作人ID
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime begin; // 操作开始时间
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end; // 操作结束时间
}