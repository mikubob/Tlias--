package com.xuan.aop;

import com.xuan.mapper.OperateLogMapper;
import com.xuan.pojo.OperateLog;
import com.xuan.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class OperateLogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    // 环绕通知，使用execution表达式拦截controller包下的增删改方法
    @Around("execution(* com.xuan.controller..*.save*(..)) || " +
            "execution(* com.xuan.controller..*.add*(..)) || " +
            "execution(* com.xuan.controller..*.insert*(..)) || " +
            "execution(* com.xuan.controller..*.update*(..)) || " +
            "execution(* com.xuan.controller..*.delete*(..)) || " +
            "execution(* com.xuan.controller..*.remove*(..)) || " +
            "execution(* com.xuan.controller..*.edit*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        
        // 获取当前操作用户ID
        Integer operateEmpId = getCurrentUserId();
        
        Object result = null;
        try {
            // 执行方法
            result = joinPoint.proceed();
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            // 当前时间
            long endTime = System.currentTimeMillis();
            // 耗时
            long costTime = endTime - startTime;

            // 构建日志对象
            OperateLog operateLog = new OperateLog();
            operateLog.setOperateEmpId(operateEmpId); // 操作人ID
            operateLog.setOperateTime(LocalDateTime.now());
            operateLog.setClassName(joinPoint.getTarget().getClass().getName());
            operateLog.setMethodName(joinPoint.getSignature().getName());
            operateLog.setMethodParams(Arrays.toString(joinPoint.getArgs()));
            if (result != null) {
                operateLog.setReturnValue(result.toString());
            } else {
                operateLog.setReturnValue("void"); // 处理返回值为null的情况
            }
            operateLog.setCostTime(costTime);

            // 插入日志
            try {
                log.info("记录操作日志:{},总共耗费时间:{}",operateLog,costTime);
                operateLogMapper.insert(operateLog);
            } catch (Exception e) {
                log.error("操作日志记录失败", e);
            }
        }
    }

    // 获取当前用户ID
    private Integer getCurrentUserId() {
        try {
            return ThreadLocalUtil.getCurrentId();
        } catch (Exception e) {
            log.error("获取当前用户ID失败", e);
        }
        return null;
    }
}