package com.xuan.filter;

import com.xuan.utils.JwtUtils;
import com.xuan.utils.ThreadLocalUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.Claims;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1.获取请求路径
        String requestURI = request.getRequestURI();// /employee/login
        //2.判断是否是登录请求，如果路径中包含/login，则是登录操作，放行
        if(requestURI.contains("/login")){
            log.info("登录请求，放行");
            filterChain.doFilter(request,response);
            return;
        }
        //3.获取请求头中的token
        String token = request.getHeader("token");
        //4.验证token是否存在，如果不存在，返回401状态码
        if(token == null||token.isEmpty()){
            log.info("token为空，返回401状态码");
            response.setStatus(401);
            return;
        }
        //5.如果token存在，则进行验证token是否正确，如果错误，返回401状态码
        Claims claims;
        try {
            claims = JwtUtils.parseJwt(token);
        } catch (Exception e) {
            log.info("token验证错误，返回401状态码");
            response.setStatus(401);
            return;
        }
        //6.如果token正确，则将用户信息保存到ThreadLocal中，并放行
        log.info("token验证成功，将用户信息保存到ThreadLocal中");
        // 从claims中获取用户ID并存入ThreadLocal
        Integer userId = ((Number) claims.get("id")).intValue();
        ThreadLocalUtil.setCurrentId(userId);
        filterChain.doFilter(request,response);

        //7.最后，将ThreadLocal中的数据清空
        log.info("将ThreadLocal中的数据清空");
        ThreadLocalUtil.remove();
    }
}