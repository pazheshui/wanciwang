package com.it.service.imp;

import com.it.mapper.TeacherMapper;
import com.it.model.teacher;
import com.it.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TeacherServiceImp implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Override
    public List<teacher> getAllTeachers() {
        List<teacher> l=teacherMapper.getAllTeachers();
        return l;
    }

    @Override
    public teacher teacherLogin(String teacherNo, String passWord) {
        teacher t=teacherMapper.teacherLogin(teacherNo,passWord);
        return  t;
    }

    @Override
    public teacher findTeaByNo(String teacherNo) {
        teacher t=teacherMapper.findTeaByNo(teacherNo);
        return  t;
}

    @Override
    public teacher findTeaByTeacherName(String teacherName) {
        teacher t=teacherMapper.findTeaByTeacherName(teacherName);
        return  t;
    }

    @Override
    public int addTeacher(teacher t) {
        int i=teacherMapper.addTeacher(t);
        return i;
    }
}
