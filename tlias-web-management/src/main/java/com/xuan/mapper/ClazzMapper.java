package com.xuan.mapper;

import com.xuan.pojo.Clazz;
import com.xuan.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClazzMapper {

    /**
     * 用于分页查询班级信息
     */
    List<Clazz> list(ClazzQueryParam clazzQueryParam);

    /**
     * 根据id删除对应的班级
     */
    // @Delete("delete from clazz where id = #{id}")
    void deleteById(Integer id);

    /**
     * 增加班级
     */
    // @Insert("insert into clazz (name, room, begin_date, end_date, master_id, subject, create_time, update_time)" +
    //         " values (#{name}, #{room}, #{beginDate}, #{endDate}, #{masterId}, #{subject}, #{createTime}, #{updateTime})")
    void insert(Clazz clazz);

    /**
     * 根据id查询班级
     */
    // @Select("select id, name, room, begin_date, end_date, master_id, subject, create_time, update_time from clazz where id=#{id}")
    Clazz getById(Integer id);

    /**
     * 修改班级
     */
    // @Update("update clazz set id=#{id},name=#{name},room=#{room},begin_date=#{beginDate},end_date=#{endDate},master_id=#{masterId},subject=#{subject} where id=#{id}")
    void update(Clazz clazz);

}