package com.xuan.controller;

import com.xuan.pojo.PageResult;
import com.xuan.pojo.Result;
import com.xuan.pojo.Student;
import com.xuan.pojo.StudentQueryParam;
import com.xuan.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 分页查询
     */
    @GetMapping
    public Result page(StudentQueryParam studentQueryParam){
        log.info("分页查询学生:{}",studentQueryParam);
        PageResult<Student> pageResult=studentService.page(studentQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 根据id批量删除学生信息
     */
    @DeleteMapping("/{ids}")
    public Result delete(@RequestParam List<Integer> ids){
        log.info("删除学生:{}",ids);
        studentService.delete(ids);
        return Result.success();
    }
    /**
     * 添加学生信息
     */
    @PostMapping
    public Result save(@RequestBody Student student){
        log.info("新增员工:{}",student);
        studentService.save(student);
        return Result.success();
    }
    /**
     * 根据id查询学生信息
     */
    @GetMapping("{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据id查询学生信息:{}",id);
        Student student = studentService.getInfo(id);
        return Result.success(student);
    }
    /**
     * 修改学员信息
     */
    @PutMapping
    public Result update(@RequestBody Student student){
        log.info("修改学生:{}",student);
        studentService.update(student);
        return Result.success(student);
    }
    /**
     * 违纪处理
     */
    @PutMapping("/violation/{id}/{score}")
    public Result violationHandle(@PathVariable Integer id , @PathVariable Integer score){
        studentService.violationHandle(id, score);
        return Result.success();
    }
}
