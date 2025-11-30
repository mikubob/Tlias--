package com.xuan.service;

import com.xuan.pojo.ClazzCountOption;
import com.xuan.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    /**
     * 统计员工职位人数
     */
    JobOption getEmpJobData();
    
    /**
     * 统计员工性别数据
     */
    List<Map<String, Object>> getEmpGenderData();

    /**
     * 统计学生学历
     */
    List<Map<String, Object>> getStudentDegreeData();

    /**
     * 统计班级人数
     */
    ClazzCountOption getStudentCountData();
}