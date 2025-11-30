package com.xuan.controller;

import com.xuan.pojo.ClazzCountOption;
import com.xuan.pojo.JobOption;
import com.xuan.pojo.Result;
import com.xuan.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/report")
@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 统计员工职位人数
     */
    @GetMapping("/empJobData")
    public Result getEmpJobData() {
        log.info("统计员工职位人数");
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }
    
    /**
     * 统计员工性别比例
     */
    @GetMapping("/empGenderData")
    public Result getEmpGenderData() {
        log.info("统计员工性别比例");
        List<Map<String,Object>> genderList = reportService.getEmpGenderData();
        return Result.success(genderList);
    }
    /**
     * 统计学生学历
     */
    @GetMapping("/studentDegreeData")
    public Result getStudentDegreeData(){
        log.info("统计学生的学历情况");
        List<Map<String,Object>> degreeList = reportService.getStudentDegreeData();
        return Result.success(degreeList);
    }
    /**
     * 班级人数统计
     */
    @GetMapping("/studentCountData")
    public Result getStudentCountData(){
        log.info("班级人数统计");
        ClazzCountOption clazzCountOption = reportService.getStudentCountData();
        return Result.success(clazzCountOption);
    }
}