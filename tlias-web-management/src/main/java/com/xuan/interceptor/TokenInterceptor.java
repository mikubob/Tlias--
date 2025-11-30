package com.xuan.interceptor;

import com.xuan.utils.JwtUtils;
import com.xuan.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import io.jsonwebtoken.Claims;

/**
 * 令牌校验拦截器
 */
@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取请求路径
        String requestURI = request.getRequestURI();
        //2.判断是否是登录请求，如果路径中包含/login，则是登录操作，放行
        if(requestURI.contains("/login")){
            log.info("登录请求，放行");
            return true;
        }
        //3.获取请求头中的token
        String token = request.getHeader("token");
        //4.验证token是否存在，如果不存在，返回401状态码
        if(token == null||token.isEmpty()){
            log.info("token为空，返回401状态码");
            response.setStatus(401);
            return false;
        }
        //5.如果token存在，则进行验证token是否正确，如果错误，返回401状态码
        Claims claims;
        try {
            claims = JwtUtils.parseJwt(token);
        } catch (Exception e) {
            log.info("token验证错误，返回401状态码");
            response.setStatus(401);
            return false;
        }
        //6.如果token正确，则将用户信息保存到ThreadLocal中，并放行
        log.info("token验证成功，将用户信息保存到ThreadLocal中");
        // 从claims中获取用户ID并存入ThreadLocal
        Integer userId = ((Number) claims.get("id")).intValue();
        ThreadLocalUtil.setCurrentId(userId);
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求处理完成后，清理ThreadLocal中的数据
        log.info("将ThreadLocal中的数据清空");
        ThreadLocalUtil.remove();
    }
}