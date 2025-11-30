package com.xuan.mapper;

import com.xuan.pojo.OperateLog;
import com.xuan.pojo.OperateQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OperateLogMapper {

    //插入日志数据
    public void insert(OperateLog log);
    
    // 分页查询操作日志
    public List<OperateLog> list(OperateQueryParam queryParam);
}