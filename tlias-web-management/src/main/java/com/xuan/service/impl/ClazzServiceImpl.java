package com.xuan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuan.mapper.ClazzMapper;
import com.xuan.mapper.DeptMapper;
import com.xuan.pojo.Clazz;
import com.xuan.pojo.ClazzQueryParam;
import com.xuan.pojo.PageResult;
import com.xuan.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;
    
    @Override
    public PageResult page(ClazzQueryParam clazzQueryParam) {
        //1.设置PageHelper分页参数
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());
        //2.执行查询
        List<Clazz> clazzList = clazzMapper.list(clazzQueryParam);
        //3.封装分页结果
        Page<Clazz> p = (Page<Clazz>)clazzList;
        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    public void deleteById(Integer id) {
        clazzMapper.deleteById(id);
    }

    @Override
    public void add(Clazz clazz) {
        //1.补全基础信息
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        //2.利用mapper接口的方法传入数据
        clazzMapper.insert(clazz);
    }

    @Override
    public Clazz getById(Integer id) {
        return clazzMapper.getById(id);
    }

    @Override
    public void update(Clazz clazz) {
        //1.修改最后一次修改时间
        clazz.setUpdateTime(LocalDateTime.now());
        //2.利用mapper接口的方法传入数据
        clazzMapper.update(clazz);
    }
    
    @Override
    public List<Clazz> list() {
        return clazzMapper.list(null);
    }
}