package com.xuan.service;

import com.xuan.pojo.PageResult;
import com.xuan.pojo.Student;
import com.xuan.pojo.StudentQueryParam;

import java.util.List;

public interface StudentService {

    /**
     * 分页查询学生
     */
    PageResult<Student> page(StudentQueryParam studentQueryParam);

    /**
     * 根据id批量删除学生
     */
    void delete(List<Integer> ids);

    /**
     * 添加学生信息
     */
    void save(Student student);

    /**
     * 根据id查询学生信息
     */
    Student getInfo(Integer id);

    /**
     * 修改学员信息
     */
    void update(Student student);

    /**
     * 违纪处理
     */
    void violationHandle(Integer id, Integer score);
}
