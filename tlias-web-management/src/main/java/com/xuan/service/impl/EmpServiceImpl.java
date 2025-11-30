package com.xuan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuan.mapper.EmpExprMapper;
import com.xuan.mapper.EmpMapper;
import com.xuan.pojo.*;
import com.xuan.service.EmpLogService;
import com.xuan.service.EmpService;
import com.xuan.utils.AliyunOSSOperator;
import com.xuan.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * The type Emp service.
 */
@Slf4j
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;
    
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    /**
     *
     * @param empQueryParam
     * @return
     */
    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        //1.设置分页数据
        PageHelper.startPage(empQueryParam.getPage(),empQueryParam.getPageSize());
        //2.执行查询语句
        List<Emp> empList = empMapper.list(empQueryParam);
        //3.解析查询语句，并封装
        Page<Emp> p= (Page<Emp>) empList;
        return new PageResult<>(p.getTotal(),p.getResult());
    }

    @Transactional(rollbackFor = {Exception.class})//事务管理
    @Override
    public void save(Emp emp) {
        try {
            //1.补全员工基础信息
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            //2.保存员工的基本信息
            empMapper.insert(emp);
            //3.保存员工的工作经历信息
            List<EmpExpr> exprList = emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){
                //遍历集合，为empId赋值
                exprList.forEach(empExpr ->empExpr.setEmpId(emp.getId()));
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            //记录操作日志
            EmpLog empLog = new EmpLog(null,LocalDateTime.now(),"新增员工"+emp);
            empLogService.insertLog(empLog);

        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(List<Integer> ids) {
        //1.查询要删除的员工的头像URL
        List<String> imageUrls = empMapper.getImagesByIds(ids);
        
        //2.批量删除员工的工作信息
        empMapper.deletByIds(ids);
        
        //3.批量删除员工的工作经历信息
        empExprMapper.deleteByEmpIds(ids);
        
        //4.删除OSS中的员工头像
        try {
            aliyunOSSOperator.deleteBatch(imageUrls);
        } catch (Exception e) {
            // 记录日志但不中断删除流程
            e.printStackTrace();
        }
    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Emp emp) {
        // 获取修改前的员工信息，用于获取旧头像URL
        Emp oldEmp = empMapper.getById(emp.getId());
        
        //1.根据id修改员工的基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);
        //2.根据id修改员工的工作经历信息
        //2.1先根据员工id删除原有的工作经历
        //empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
        empExprMapper.deleteByEmpIds(Collections.singletonList(emp.getId()));
        //2.2再添加这个员工新的工作经历
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(empExpr -> empExpr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }
        
        // 处理头像更新逻辑
        // 如果新旧头像URL不同，且新头像URL不为空，则删除旧头像
        if (oldEmp != null && oldEmp.getImage() != null && emp.getImage() != null 
            && !oldEmp.getImage().equals(emp.getImage())) {
            try {
                // 删除旧头像
                aliyunOSSOperator.deleteBatch(Collections.singletonList(oldEmp.getImage()));
            } catch (Exception e) {
                // 记录日志但不中断更新流程
                e.printStackTrace();
            }
        }
    }

    @Override
    public LoginInfo login(Emp emp) {
        //1.调用mapper接口，根据用户名和密码查询员工信息
        Emp e = empMapper.selectByUsernameAndPassword(emp);
        //2.判断员工信息是否为空，如果为空则返回null，否则返回员工信息
        if(e != null){
            log.info("登录成功,员工信息:{}",e);
            //生成jwt令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",e.getId());
            claims.put("username",e.getUsername());
            String jwt = JwtUtils.generateJwt(claims);
            return new LoginInfo(e.getId(),e.getUsername(),e.getName(),jwt);
        }else{
            return null;
        }
    }
    /**
     *
     * @param page 页码
     * @param pageSize 每页显示记录数
     * @return
     */
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize){
//        //1.设置分页数据
//        PageHelper.startPage(page,pageSize);
//        //2.执行查询语句
//        List<Emp> empList = empMapper.list();
//        //3.解析查询语句，并封装
//        Page<Emp> p= (Page<Emp>) empList;
//        return new PageResult<>(p.getTotal(),p.getResult());
//    }
}