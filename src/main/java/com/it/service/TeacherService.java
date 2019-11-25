package com.it.service;

import com.it.model.teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherService {
    List<teacher> getAllTeachers();
    teacher teacherLogin(String teacherNo,String passWord);
    teacher findTeaByNo(String teacherNo);
    teacher findTeaByTeacherName(String teacherName);
    int addTeacher(teacher t);
}
