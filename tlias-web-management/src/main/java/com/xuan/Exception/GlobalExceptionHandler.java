package com.xuan.Exception;

import com.xuan.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result handleException(Exception e){
        log.error("程序出错了~",e);
        return Result.error("出错了，请联系管理员~");
    }

    @ExceptionHandler
    public Result handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        log.error("程序出错了~",e);
        String message = e.getMessage();
        if(message.contains("Duplicate entry")) {
            int i = message.indexOf("Duplicate entry");
            String errorMessage = message.substring(i);
            String[] arr = errorMessage.split(" ");
            return Result.error("数据已存在：" + arr[2]);
        }
        return Result.error("数据约束违反");
    }

    @ExceptionHandler
    public Result handleSpringDuplicateKeyException(DuplicateKeyException e){
        log.error("程序出错了~",e);
        String message = e.getMessage();
        if(message.contains("Duplicate entry")) {
            int i = message.indexOf("Duplicate entry");
            String errorMessage = message.substring(i);
            String[] arr = errorMessage.split(" ");
            return Result.error("数据已存在：" + arr[2]);
        }
        return Result.error("数据约束违反");
    }

    @ExceptionHandler
    public Result handleDeptHasEmployeesException(DeptHasEmployeesException e) {
        log.error("删除部门时出错：{}", e.getMessage());
        return Result.error(e.getMessage());
    }
}
