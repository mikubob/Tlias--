package com.xuan.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class StudentQueryParam {
    private Integer page = 1;//页码
    private Integer pageSize = 10;//每页显示数量
    
    // 计算偏移量
    public Integer getOffset() {
        return (page - 1) * pageSize;
    }
    
    private String name;// 姓名
    private Integer gender;// 性别
    private String no;// 学号
    private Integer clazzId;// 班级ID
    private Integer degree;// 最高学历
    private Integer isCollege;// 是否来自于院校
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;//创建时间起始
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;//创建时间结束
}