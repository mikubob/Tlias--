package com.xuan.service.impl;

import com.xuan.mapper.EmpMapper;
import com.xuan.mapper.StudentMapper;
import com.xuan.pojo.ClazzCountOption;
import com.xuan.pojo.JobOption;
import com.xuan.pojo.Student;
import com.xuan.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 统计员工职位人数
     */
    @Override
    public JobOption getEmpJobData() {
        //1.调用mapper接口，获取统计数据
        List<Map<String, Object>> list = empMapper.countEmpJobData();
        //2.组装结果，并且返回数据
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("num")).toList();
        return new JobOption(jobList,dataList);
    }
    
    /**
     * 统计员工性别数据
     */
    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }

    /**
     * 统计学生学历
     */
    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return studentMapper.countStudentDegreeData();
    }

    @Override
    public ClazzCountOption getStudentCountData() {
        //1.调用mapper接口，获取统计数据
        List<Map<String,Object>> list = studentMapper.countStudentCountData();
        //2.组装结构并且返回数据
        List<Object> clazzList = list.stream().map(dataMap -> dataMap.get("cname")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("scount")).toList();
        return new ClazzCountOption(clazzList,dataList);
    }
}