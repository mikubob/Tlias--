package com.xuan.service.impl;

import com.xuan.mapper.DeptMapper;
import com.xuan.pojo.Dept;
import com.xuan.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @Override
    public void deleteById(Integer id) throws com.xuan.Exception.DeptHasEmployeesException {
        // 检查部门下是否有员工
        Integer employeeCount = deptMapper.countEmployeesByDeptId(id);
        if (employeeCount > 0) {
            throw new com.xuan.Exception.DeptHasEmployeesException("对不起，当前部门下有员工，不能直接删除！");
        }
        deptMapper.deleteById(id);
    }

    @Override
    public void add(Dept dept) {
        //1.补全基础属性
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        //2.利用mapper接口方法传入数据
        deptMapper.insert(dept);
    }

    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    @Override
    public void update(Dept dept) {
        //1.补全基础属性
        dept.setUpdateTime(LocalDateTime.now());
        //2.利用mapper接口的方法传入数据
        deptMapper.update(dept);
    }
}
