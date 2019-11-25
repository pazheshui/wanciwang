package com.it.model;

import javax.persistence.Transient;
import java.util.List;

public class student {
    private int id;
    private String userName;
    private String passWord;
    private String className;
    private String teacherId;
    private String studentNo;

    private List<task> taskList;
    private List<score> scoreList;
    @Transient
    private int allGrade;

    public List<task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<task> taskList) {
        this.taskList = taskList;
    }

    public List<score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<score> scoreList) {
        this.scoreList = scoreList;
    }

    public int getAllGrade() {
        return allGrade;
    }

    public void setAllGrade(int allGrade) {
        this.allGrade = allGrade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    @Override
    public String toString() {
        return "student{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", className='" + className + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", studentNo='" + studentNo + '\'' +
                '}';
    }
}
