package com.xuan.aop;

import com.xuan.mapper.LoginMapper;
import com.xuan.pojo.Emp;
import com.xuan.pojo.EmpLoginLog;
import com.xuan.pojo.LoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.xuan.pojo.Result;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoginLogAspect {
    @Autowired
    private LoginMapper loginMapper;
    
    @Around("execution(* com.xuan.controller.LoginController.*login*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //记录开始时间
        long startTime = System.currentTimeMillis();

        //执行原始方法,获取返回值是Result类型
        Object result = joinPoint.proceed();
        //记录结束时间
        long endTime = System.currentTimeMillis();
        //总耗时
        long costTime=endTime-startTime;

        //构建日志对象
        EmpLoginLog empLoginLog=new EmpLoginLog();
        empLoginLog.setCostTime(costTime);//登录耗时
        empLoginLog.setLoginTime(LocalDateTime.now()); // 设置登录时间

        //获取参数,该参数唯一的值就是emp
        Object[] args = joinPoint.getArgs();
        //把获取到的转换成字符串，输出到日志中
        String emps = Arrays.toString(args);
        log.info("登录操作参数:{}",emps);

        if(args[0] instanceof Emp){//在这里检查args[0]的类型是否可以是Emp的实例类型
            Emp emp=(Emp) args[0];
            empLoginLog.setUsername(emp.getUsername());
            empLoginLog.setPassword(emp.getPassword());
        }

        if(result instanceof Result){
            Result res=(Result) result;
            if(res.getCode()==1) {
                empLoginLog.setIsSuccess((short) 1);
                Object data = res.getData();
                if(data instanceof LoginInfo){
                    LoginInfo loginInfo=(LoginInfo) data;
                    empLoginLog.setJwt(loginInfo.getToken());
                }
            }
            if(res.getCode()==0){
                empLoginLog.setIsSuccess((short) 0);
            }
        }
        
        // 保存登录日志
        loginMapper.save(empLoginLog);
        
        return result;
    }
}