package com.xuan.controller;

import com.xuan.pojo.Clazz;
import com.xuan.pojo.ClazzQueryParam;
import com.xuan.pojo.PageResult;
import com.xuan.pojo.Result;
import com.xuan.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clazzs")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    /**
     * 用于分页查询班级信息
     */
    @GetMapping
    public Result page(ClazzQueryParam clazzQueryParam) {
        log.info("查询请求参数：{}", clazzQueryParam);
        PageResult pageResult = clazzService.page(clazzQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 根据id删除对应班级
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id){
        log.info("根据id删除的部门为:{}",id);
        clazzService.deleteById(id);
        return Result.success();
    }

    /**
     * 添加班级
     */
    @PostMapping
    public Result add(@RequestBody Clazz clazz){
        log.info("添加的班级数据为:{}",clazz);
        clazzService.add(clazz);
        return Result.success();
    }

    /**
     * 根据id查询班级信息
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable("id") Integer id){
        log.info("根据id查询班级:{}",id);
        Clazz clazz = clazzService.getById(id);
        return Result.success(clazz);
    }

    /**
     * 修改班级信息
     */
    @PutMapping
    public Result update(@RequestBody Clazz clazz){
        log.info("修改的班级为:{}",clazz);
        clazzService.update(clazz);
        return Result.success();
    }
    
    /**
     * 查询所有班级信息
     */
    @GetMapping("/list")
    public Result list() {
        log.info("查询全部班级信息");
        List<Clazz> clazzList = clazzService.list();
        return Result.success(clazzList);
    }
}