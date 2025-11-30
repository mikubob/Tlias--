package com.xuan.mapper;

import com.xuan.pojo.EmpExpr;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
* 员工工作经历
* */
@Mapper
public interface EmpExprMapper {
    /**
     * 批量保存员工的经历
     *
     * @param exprList
     */
    // @Insert("insert into emp_expr(emp_id,begin,end,company,job) values ...")
    public  void insertBatch(List<EmpExpr> exprList);

    /**
     * 根据id批量删除员工经历信息
     * @param EmpIds
     */
    // void deleteByEmpIds(List<Integer> EmpIds);
    void deleteByEmpIds(List<Integer> EmpIds);
}