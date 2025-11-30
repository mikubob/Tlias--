package com.xuan.service;

import com.github.pagehelper.PageInfo;
import com.xuan.pojo.OperateLog;
import com.xuan.pojo.OperateQueryParam;

public interface OperateLogService {
    // 分页查询操作日志
    PageInfo<OperateLog> page(OperateQueryParam queryParam);
}