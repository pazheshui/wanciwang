package com.it.mapper;

import com.it.model.task;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TaskMapper {
    @Insert("insert into task(title,content,createTime,explainFile,teacherNo) values(#{title},#{content},#{createTime},#{explainFile},#{teacherNo})")
    @Options(useGeneratedKeys=true, keyProperty="id")//返回插入数据后的id的配置
    int addTask(task t);
    @Select("select * from task where teacherNo=#{teacherNo}")
    List<task> findTasksByTeacherNo(@Param("teacherNo")String teacherNo);
    @Update("update task set title=#{title},content=#{content},explainFile=#{explainFile} where id=${id}")
    int updateTaskById(task t);
    @Delete("delete from task where id=${id}")
    int deleteTaskById(task t);
    @Select("select * from task where studentNo=#{studentNo}")
    List<task> findTasksByStudentNo(@Param("studentNo")String studentNo);

    @Select("select * from task where id=#{id}")
    List<task> findTasksById(@Param("id")int id);

    @Select("select * from task where id=#{id}")
    task findOneTasksById(@Param("id")int id);

    @Update("update task set explainFile=#{explainFile} where id=#{id}")
    int updateTaskExplainFile(task t);
}
