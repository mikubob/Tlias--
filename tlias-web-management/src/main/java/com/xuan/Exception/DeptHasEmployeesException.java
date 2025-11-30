package com.xuan.Exception;

public class DeptHasEmployeesException extends RuntimeException {
    public DeptHasEmployeesException(String message) {
        super(message);
    }
}