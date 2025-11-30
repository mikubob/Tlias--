package com.xuan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuan.mapper.EmpMapper;
import com.xuan.mapper.StudentMapper;
import com.xuan.pojo.EmpLog;
import com.xuan.pojo.PageResult;
import com.xuan.pojo.Student;
import com.xuan.pojo.StudentQueryParam;
import com.xuan.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    /**
     * 分页查询学生
     */
    @Override
    public PageResult<Student> page(StudentQueryParam studentQueryParam) {
        //1.设置分页数据
        PageHelper.startPage(studentQueryParam.getPage(),studentQueryParam.getPageSize());
        //2.执行查询语句
        List<Student> studentList = studentMapper.list(studentQueryParam);
        //3.解析查询语句，并且封装
        Page<Student> p = (Page<Student>) studentList;
        return new PageResult<>(p.getTotal(),p.getResult());
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(List<Integer> ids) {
        try {
            //批量删除学生的信息
            studentMapper.deleteByIds(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Student student) {
            //1.补全员基础信息
            student.setCreateTime(LocalDateTime.now());
            student.setUpdateTime(LocalDateTime.now());
            //2.保存员工基本信息
            studentMapper.insert(student);

    }

    @Override
    public Student getInfo(Integer id) {
        return studentMapper.getById(id);
    }

    @Override
    public void update(Student student) {
        //根据id修改员工的基本信息
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.updateById(student);
    }

    @Override
    public void violationHandle(Integer id, Integer score) {
        studentMapper.updateViolation(id, score);
    }
}