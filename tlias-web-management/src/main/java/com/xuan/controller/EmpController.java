package com.xuan.controller;

import com.xuan.pojo.Emp;
import com.xuan.pojo.EmpQueryParam;
import com.xuan.pojo.PageResult;
import com.xuan.pojo.Result;
import com.xuan.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/*
 * 员工管理的controller
 * */
@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;
    /*
    * 分页查询
    * */
//    @GetMapping
//    public Result page(@RequestParam(defaultValue = "1") Integer page,
//                       @RequestParam(defaultValue = "10") Integer pageSize){
//        log.info("分页查询：{}，{}",page,pageSize);
//        PageResult<Emp> pageResult = empService.page(page,pageSize);
//        return Result.success(pageResult);
//    }

    //对员工信息进行分页查询
    @GetMapping
    public Result page(EmpQueryParam empQueryParam){
        log.info("分页查询员工：{}",empQueryParam);
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }

    //新增员工
    @PostMapping
    public Result save(@RequestBody Emp emp){
        log.info("新增员工:{}",emp);
        empService.save(emp);
        return Result.success();
    }

    //删除员工
    /*@DeleteMapping
    public Result delete(Integer[] ids){
        log.info("删除员工:{}", Arrays.toString(ids));
        return Result.success();
    }*/
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        log.info("删除员工:{}",ids);
        empService.delete(ids);
        return Result.success();
    }

    //通过id查询员工的详细信息
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据id查询员工详情:{}",id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

    //修改员工的数据
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("修改员工:{}",emp);
        empService.update(emp);
        return Result.success(emp);
    }
}