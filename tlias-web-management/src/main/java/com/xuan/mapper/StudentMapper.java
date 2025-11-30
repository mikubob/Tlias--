package com.xuan.mapper;

import com.xuan.pojo.Student;
import com.xuan.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {
    /**
     * 分页查询学生
     */
    List<Student> list(StudentQueryParam studentQueryParam);

    /**
     * 批量删除学生
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 添加学生
     */
    @Insert("insert into student(name, no, gender, phone,id_card, is_college, address, degree, graduation_date,clazz_id, create_time, update_time) VALUES " +
            "(#{name},#{no},#{gender},#{phone},#{idCard},#{isCollege},#{address},#{degree},#{graduationDate},#{clazzId},#{createTime},#{updateTime})")
    void insert(Student student);

    /**
     * 根据id查询学生信息
     */
    Student getById(Integer id);

    /**
     * 修改学员信息
     */
    void updateById(Student student);

    /**
     * 违纪处理
     */
    @Update("update student set violation_count = violation_count + 1 , violation_score = violation_score + #{score} , update_time = now() where id = #{id}")
    void updateViolation(Integer id, Integer score);

    /**
     * 统计学生学历
     */
    @MapKey("name")
    List<Map<String, Object>> countStudentDegreeData();

    /**
     * 统计班级人数
     */
    @Select("select c.name cname , count(s.id) scount from clazz c  left join student s on s.clazz_id = c.id group by c.name order by count(s.id) desc ")
    List<Map<String,Object>> countStudentCountData();
}