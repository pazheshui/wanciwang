package com.it.model;



import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

public class task {
    private int id;
    private String title;
    private String content;
    private Date createTime;
    private String explainFile;
    private String teacherNo;
    @Transient//表示这个数据不与数据库字段形成映射
    List<score> scores;

    public List<score> getScores() {
        return scores;
    }

    public void setScores(List<score> scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", explainFile='" + explainFile + '\'' +
                ", teacherNo='" + teacherNo + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExplainFile() {
        return explainFile;
    }

    public void setExplainFile(String explainFile) {
        this.explainFile = explainFile;
    }

    public String getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(String teacherNo) {
        this.teacherNo = teacherNo;
    }
}
