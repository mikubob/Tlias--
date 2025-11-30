package com.xuan.service;

import com.xuan.pojo.Dept;

import java.util.List;

public interface DeptService {
    /*
    * 查询所有的部门数据
    * */
    List<Dept> findAll();

    /*
    * 删除对应id的部门的数据
    * */
    void deleteById(Integer id) throws com.xuan.Exception.DeptHasEmployeesException;

    /*
    * 增加部门信息
    * */
    void add(Dept dept);

    /*
    * 根据id查询数据
    * */
    Dept getById(Integer id);

    /*
    * 修改部门数据
    * */
    void update(Dept dept);
}

