package com.it.mapper;

import com.it.model.score;
import com.it.model.student;
import com.it.model.task;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScoreMapper {
    @Insert("insert into score(studentNo,taskId) values(#{studentNo},#{taskId})")
    int addScore(score s);

    //查找接受同一任务的学生们的成绩
    @Select("select * from score  join student on score.studentNo=student.studentNo where taskId=#{taskId}")
    List<score> findScoresByTaskId(@Param("taskId")String taskId);

    //任务表和成绩表
    @Select("select * from score join task on score.taskId=task.id where studentNo=#{studentNo}")
    List<score> findScoresByStudentNo(@Param("studentNo")String studentNo);

    @Delete("delete from score where taskId=#{taskId}")
    int deleteScoreByTaskId(String taskId);

    @Delete("delete from score where id=${id}")
    int deleteScoreById(@Param("id")int id);
    //任务下学生成绩页面

    @Select("select * from score where taskId=#{taskId}")
    @Results(value={
        @Result(property="s",column ="studentNo",one=@One(select="com.it.mapper.StudentMapper.findStudentByStudentNo"))
    }
    )
    List<score> findScoreByTaskId2(@Param("taskId")String taskId);

    @Update("update score set status=#{status} where id=${id}")
    int updateScoreStatusById(score s);

    @Select("select * from score where studentNo=#{studentNo}")
    @Results(value={
            @Result(property="t",column ="taskId",one=@One(select="com.it.mapper.TaskMapper.findOneTasksById"))
    }
    )
    List<score> findScoreByStudentNo(@Param("studentNo")String studentNo);

//    @Result(property="taskId",column ="taskId")
    @Update("update score set homeWorkFile=#{homeWorkFile} where id=#{id}")
    int updateScoreById(score s);

    @Select("select * from score where id=${id}")
    score getScoreById(@Param("id") int id);

    @Delete("delete from score where studentNo=#{studentNo}")
    int deleteScoreByStudentNo(@Param("studentNo")String studentNo);
}
