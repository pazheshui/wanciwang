package com.it.service;



import com.github.pagehelper.PageInfo;
import com.it.model.task;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskService {
    int addTask(task t,String[] selectedStudentTd,MultipartFile[] files);
//    PageInfo<task> findTasksByTeacherNo(int pageIndex, int pageSize, String teacherNo);
    List<task> findTasksByTeacherNoPage(String teacherNo);
    int updateTaskById(task t,String[] stuList,String oldExplainFile);
    int deleteTaskById(task t);
    //对任务列表分页
    PageInfo<task> getTaskWithPage(String teacherNo,int curr,int size);

    task findOneTasksById(int id);

    int updateTaskExplainFile(task t,String explainFile);
}
