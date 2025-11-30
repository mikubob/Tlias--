package com.xuan.mapper;

import com.xuan.pojo.EmpLoginLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {

    @Insert("insert into emp_login_log (username, password, login_time, is_success, jwt, cost_time) " +
            "values (#{username}, #{password}, #{loginTime}, #{isSuccess}, #{jwt}, #{costTime})")
    void save(EmpLoginLog empLoginLog);
}