package com.it.service;

import com.github.pagehelper.PageInfo;
import com.it.model.student;
import com.it.model.task;

import java.util.List;

public interface StudentService {
    List<student> gets();
    List<student> findStudentByTeacherId(String teacherId);
    int addStudent(student s);
    List<student> findStudentByTeacherId2(String teacherId);
    student studentLogin(student s);
    List<student> updateTaskGetStu(String teacherNo,String taskId);
    PageInfo<student> getStuWithPage(String teacherId, int curr, int size);
    int testAddStudent(student s);

    int updateStudentPassWord(student s);

    int deleteStudentByStudentNo(student s);
}
