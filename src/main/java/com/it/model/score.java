package com.it.model;

import javax.persistence.Transient;

public class score {
    private int id;
    private String studentNo;
    private String taskId;
    private String grade;
    private String homeWorkFile;
    private String explain1;
    private String status;
    private student s;
    private task t;
    @Transient
    private String title;
    @Transient
    private String userName;

    public task getT() {
        return t;
    }

    public void setT(task t) {
        this.t = t;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public student getS() {
        return s;
    }

    public void setS(student s) {
        this.s = s;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getHomeWorkFile() {
        return homeWorkFile;
    }

    public void setHomeWorkFile(String homeWorkFile) {
        this.homeWorkFile = homeWorkFile;
    }

    public String getExplain1() {
        return explain1;
    }

    public void setExplain1(String explain1) {
        this.explain1 = explain1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
