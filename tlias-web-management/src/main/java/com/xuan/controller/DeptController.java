package com.xuan.controller;

import com.xuan.pojo.Dept;
import com.xuan.pojo.Result;
import com.xuan.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理控制器
 * <p>
 * 提供以下功能：
 * 1. 查询所有部门
 * 2. 根据ID删除部门
 * 3. 添加新部门
 * 4. 根据ID查询部门详情
 * 5. 修改部门信息
 */
@Slf4j
@RestController
@RequestMapping("/depts")
public class DeptController {

    //private static final Logger log= LoggerFactory.getLogger(DeptController.class);//固定的


    @Autowired
    private DeptService deptService;

    /**
     * 查询全部部门数据
     *
     * @return 部门列表
     */
    @GetMapping
    public Result list() {
        //System.out.println("查询全部的部门数据");
        log.info("查询全部的部门数据");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    /**
     * 根据ID删除部门
     *
     * @param deptId 部门ID
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestParam(value = "id", required = false) Integer deptId) {
        //System.out.println("根据ID删除部门：" + deptId);
        log.info("根据id删除部门:{}",deptId);
        try {
            deptService.deleteById(deptId);
            return Result.success();
        } catch (com.xuan.Exception.DeptHasEmployeesException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 添加部门
     *
     * @param dept 部门信息
     * @return 添加结果
     */
    @PostMapping
    public Result add(@RequestBody Dept dept) {
        //System.out.println("添加的部门数据为：" + dept);
        log.info("添加的部门数据为:{}",dept);
        deptService.add(dept);
        return Result.success();
    }

    /**
     * 根据ID查询部门详情
     *
     * @param id 部门ID
     * @return 部门信息
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable("id") Integer id) {
        log.info("根据id查询部门:{}",id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    /**
     * 修改部门信息
     *
     * @param dept 部门信息
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody Dept dept) {
        //System.out.println("修改的部门为：" + dept);
        log.info("修改的部门为:{}",dept);
        deptService.update(dept);
        return Result.success();
    }
}