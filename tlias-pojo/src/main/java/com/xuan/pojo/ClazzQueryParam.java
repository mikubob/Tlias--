package com.xuan.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ClazzQueryParam {
    private Integer page = 1;//页码
    private Integer pageSize = 10;//每页显示数量
    private String name;// 班级名称
    private Integer masterId;// 班主任ID
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginDate;//开课时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;//结课时间
}