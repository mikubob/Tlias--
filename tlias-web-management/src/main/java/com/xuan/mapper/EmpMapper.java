package com.xuan.mapper;

import com.xuan.pojo.Emp;
import com.xuan.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/*
* 员工信息
* */
@Mapper
public interface EmpMapper {
    //-------------------------------------原始分页查询语句
   /*
   * 查询记录总数
   * */
//   @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
//   public long count();
   /*
   * 分页查询
   * */
//   @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id " +
//           "order by e.update_time desc limit #{start},#{pageSize}")
//   public List<Emp> list(Integer start,Integer pageSize);
//    @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id " +
//            "order by e.update_time desc")
//  public List<Emp> list();

    /**
     * 条件查询员工的信息
     * @param empQueryParam
     * @return
     */
    List<Emp> list(EmpQueryParam empQueryParam);

    /**
     * 新增员工的基础信息
     * @param emp
     */
    @Options(useGeneratedKeys = true,keyProperty = "id")//获取到生成的主键-----主键返回
    // @Insert("insert into emp(username,name,gender,phone,job,salary,image,entry_date,dept_id,create_time,update_time)"
    // +"values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);

    /**
     * 根据id批量删除员工信息
     * @param ids
     */
    void deletByIds(List<Integer> ids);
    
    /**
     * 根据员工ID列表查询员工头像URL
     * @param ids 员工ID列表
     * @return 员工头像URL列表
     */
    List<String> getImagesByIds(List<Integer> ids);

    /**
     * 根据id查询员工信息以及对应的工作经历信息
     * @param id
     * @return
     */
    Emp getById(Integer id);

    /**
     * 根据对应的id修改员工的信息和工作经历
     * @param emp
     */
    void updateById(Emp emp);

    /**
     * 统计员工职位人数
     * @return
     */
    List<Map<String, Object>> countEmpJobData();
    
    /**
     * 统计员工性别数据
     * @return
     */
    List<Map<String, Object>> countEmpGenderData();

    /**
     * 根据用户名和密码查询员工信息
     */
    @Select("select id, username, password, name from emp where username=#{username} and password=#{password}")
    Emp selectByUsernameAndPassword(Emp emp);
}