package com.it.javabean;

import org.springframework.web.multipart.MultipartFile;

public class ReleaseTaskDTO {
    private String teacherNo;

    private String taskContent;
    private String taskTitle;
    private String[] selectedStudentTd;
    private MultipartFile[] files;
    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String[] getSelectedStudentTd() {
        return selectedStudentTd;
    }

    public void setSelectedStudentTd(String[] selectedStudentTd) {
        this.selectedStudentTd = selectedStudentTd;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public String getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(String teacherNo) {
        this.teacherNo = teacherNo;
    }
}
