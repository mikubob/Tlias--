package com.xuan.service;

import com.xuan.pojo.Clazz;
import com.xuan.pojo.ClazzQueryParam;
import com.xuan.pojo.PageResult;

import java.util.List;

public interface ClazzService {
    /**
     * 用于分页查询班级信息
     */
    PageResult page(ClazzQueryParam clazzQueryParam);

    /**
     * 根据id删除对应的班级
     */
    void deleteById(Integer id);

    /**
     * 添加部门信息
     */
    void add(Clazz clazz);

    /**
     * 根据id查询班级
     */
    Clazz getById(Integer id);

    /**
     * 修改班级数据
     */
    void update(Clazz clazz);
    
    /**
     * 查询所有班级信息
     */
    List<Clazz> list();
}