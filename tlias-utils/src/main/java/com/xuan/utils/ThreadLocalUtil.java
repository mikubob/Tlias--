package com.xuan.utils;

public class ThreadLocalUtil {

    private static final ThreadLocal<Integer> CURRENT_LOCAL = new ThreadLocal<>();

    // 设置当前用户ID
    public static void setCurrentId(Integer employeeId) {
        CURRENT_LOCAL.set(employeeId);
    }

    // 获取当前用户ID
    public static Integer getCurrentId() {
        return CURRENT_LOCAL.get();
    }

    // 删除当前用户ID
    public static void remove() {
        CURRENT_LOCAL.remove();
    }
}