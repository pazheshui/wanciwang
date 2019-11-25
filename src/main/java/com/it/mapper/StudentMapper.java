package com.it.mapper;//package com.it.mapper;

import com.it.model.student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("select * from student")
    List<student> gets();
    //我的学生页面数据
    @Select("select * from student where teacherId=#{teacherId}")
    @Results(value={
            @Result(property = "studentNo",column = "studentNo"),
            @Result(property="scoreList",column ="studentNo",many = @Many(select="com.it.mapper.ScoreMapper.findScoresByStudentNo"))
    }
    )
    List<student> findStudentByTeacherId(@Param("teacherId")String teacherId);
    @Select("select * from student where studentNo=#{studentNo}")
    student findStudentByStudentNo(@Param("studentNo")String studentNo);
    @Insert("insert into student(userName,className,teacherId,studentNo) values(#{userName},#{className},#{teacherId},#{studentNo})")
    int addStudent(student s);
    @Select("select * from student where studentNo=#{studentNo} and passWord=#{passWord}")
    student studentLogin(student s);


    @Insert("insert into student(userName,className,studentNo) values(#{userName},#{className},#{studentNo})")
    int testAddStudent(student s);


    @Update("update student set passWord=#{passWord} where studentNo=#{studentNo}")
    int updateStudentPassWord(student s);

    @Delete("delete from student where studentNo=#{studentNo}")
    int deleteStudentByStudentNo(student s);
}
