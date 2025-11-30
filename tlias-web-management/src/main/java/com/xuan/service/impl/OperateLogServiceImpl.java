package com.xuan.service.impl;

import com.github.pagehelper.PageInfo;
import com.xuan.mapper.OperateLogMapper;
import com.xuan.pojo.OperateLog;
import com.xuan.pojo.OperateQueryParam;
import com.xuan.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperateLogServiceImpl implements OperateLogService {
    
    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public PageInfo<OperateLog> page(OperateQueryParam queryParam) {
        List<OperateLog> operateLogs = operateLogMapper.list(queryParam);
        return new PageInfo<>(operateLogs);
    }
}