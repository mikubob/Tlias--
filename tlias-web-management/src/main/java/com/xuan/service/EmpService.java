package com.xuan.service;

import com.xuan.pojo.Emp;
import com.xuan.pojo.EmpQueryParam;
import com.xuan.pojo.LoginInfo;
import com.xuan.pojo.PageResult;

import java.util.List;

/**
 * 员工管理服务接口
 * 提供员工相关的业务处理方法
 */
public interface EmpService {
    /**
     * 分页查询
     * @param empQueryParam
     * @return
     */
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    /**
     * 新增员工信息
     * @param emp
     */
    void save(Emp emp);

    /**
     * 删除员工信息
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 根据id查询员工数据
     * @param id
     * @return
     */
    Emp getInfo(Integer id);

    /**
     * 修改员工数据
     * @param emp
     */
    void update(Emp emp);

    /**
     * 登录
     */
    LoginInfo login(Emp emp);
}