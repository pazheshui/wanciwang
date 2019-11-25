package com.it.mapper;

import com.it.model.teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherMapper {
    @Select("select * from teacher")
    List<teacher> getAllTeachers();
    @Select("select * from teacher where teacherNo=#{teacherNo} and passWord=#{passWord}")
    teacher teacherLogin(@Param("teacherNo")String teacherNo,@Param("passWord")String passWord);

    @Select("select * from teacher where teacherNo=#{teacherNo}")
    teacher findTeaByNo(@Param("teacherNo")String teacherNo);

    @Insert("insert into teacher(teacherName,passWord,teacherNo,telphone) values(#{teacherName},#{passWord},#{teacherNo},#{telphone})")
    int addTeacher(teacher s);

    @Select("select * from teacher where teacherName=#{teacherName}")
    teacher findTeaByTeacherName(@Param("teacherName")String teacherName);
}
