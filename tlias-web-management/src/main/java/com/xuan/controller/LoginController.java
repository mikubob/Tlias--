package com.xuan.controller;

import com.xuan.pojo.Emp;
import com.xuan.pojo.LoginInfo;
import com.xuan.pojo.Result;
import com.xuan.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录Controller
 */
@Slf4j
@RestController
@RequestMapping ("/login")
public class LoginController {

    @Autowired
    private EmpService empService;

    /**
     * 登录
     */
    @PostMapping
     public Result login(@RequestBody Emp emp){
        log.info("登录:{}",emp);
        LoginInfo loginInfo = empService.login(emp);
        if(loginInfo != null){
            return Result.success(loginInfo);
        }else{
            return Result.error("用户名或密码错误");
        }
    }
}
