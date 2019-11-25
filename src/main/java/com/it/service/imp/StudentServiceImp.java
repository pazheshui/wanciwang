package com.it.service.imp;



import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.mapper.ScoreMapper;
import com.it.mapper.StudentMapper;
import com.it.model.score;
import com.it.model.student;
import com.it.model.task;
import com.it.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class StudentServiceImp implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ScoreMapper scoreMapper;
    @Override
    public List<student> gets() {
        List<student> l=studentMapper.gets();
        return  l;
    }

    @Override
    public int testAddStudent(student s) {
        int i=studentMapper.testAddStudent(s);
        return i;
    }

    @Override
    public int updateStudentPassWord(student s) {
        int i=studentMapper.updateStudentPassWord(s);
        return i;
    }

    @Override
    public int deleteStudentByStudentNo(student s) {
        int i=studentMapper.deleteStudentByStudentNo(s);
        return i;
    }

    @Override
    public List<student> findStudentByTeacherId(String teacherId) {
        List<student> l=studentMapper.findStudentByTeacherId(teacherId);
        //去除未完成的成绩列表,顺便获取总成绩
        for(student s:l){
            int i=0;
            List<score> scoreList=s.getScoreList();//成绩表
            if(scoreList.size()>0){//不为0，就去遍历它
                Iterator<score> iterator = scoreList.iterator();
                while (iterator.hasNext()) {
                    score ss = iterator.next();
                    if ("1".equals(ss.getStatus())) {
                        i=i+1;
                    }else{
                        iterator.remove();//使用迭代器的删除方法删除
                    }
                }
            }
            s.setAllGrade(i);

        }


        return l;
    }
    public List<student> findStudentByTeacherId2(String teacherId) {//这个返回的学生信息包含平均分和接受任务总数
        List<student> l=studentMapper.findStudentByTeacherId(teacherId);
//        if(l.size()>0){
//            for(student s:l){//遍历学生设置额外的数据,用每个学生的studentNo去score查
//                float avg=0;
//                int allCount=0;
//                String stuNo=s.getStudentNo();
//                List<score> scores=scoreMapper.findScoresByStudentNo(stuNo);
//                if(scores.size()>0){
//                    allCount=scores.size();//学生的任务总数
//                    //根据每个学生的成绩列表计算出平均分
//                    float allGrade = 0;
//                    for(score sco:scores){
//                        String gra=sco.getGrade();
//                        allGrade+=Float.parseFloat(gra);
//                    }
//                    avg=allGrade/allCount;
//                }
//                //设置
//                s.setAvgGrade(avg);
//                s.setAcceptTaskCount(allCount+"");
//            }
//        }
        return l;
    }

    @Override
    public student studentLogin(student s) {
        student ss=studentMapper.studentLogin(s);
        return  ss;
    }

    @Override
    public List<student> updateTaskGetStu(String teacherNo, String taskId) {
        System.out.println("taskId"+taskId);
        List<score> scoreList=scoreMapper.findScoresByTaskId(taskId);
        System.out.println("scoreList的长度是"+scoreList.size());
        List<student> studentList=studentMapper.findStudentByTeacherId(teacherNo);
        System.out.println("studentList的长度是"+studentList.size());
        //从学生列表去除重复的部分
        Iterator<student> iterator = studentList.iterator();
        while (iterator.hasNext()) {
            student stu=iterator.next();
            for(int i=0;i<scoreList.size();i++){
                score sco=scoreList.get(i);
                String scoStudentNo=sco.getStudentNo();
                String stuStudentNo=stu.getStudentNo();
                if(scoStudentNo.equals(stuStudentNo)){
                    iterator.remove();
                }
            }
        }      System.out.println("结果长度是"+studentList.size());
        return studentList;
    }

    @Override
    public PageInfo<student> getStuWithPage(String teacherId, int curr, int size) {
        PageHelper.startPage(curr,size);
        List<student> l =studentMapper.findStudentByTeacherId(teacherId);
        for(student s:l){
            int i=0;
            List<score> scoreList=s.getScoreList();//成绩表
            if(scoreList.size()>0){//不为0，就去遍历它
                Iterator<score> iterator = scoreList.iterator();
                while (iterator.hasNext()) {
                    score ss = iterator.next();
                    if ("1".equals(ss.getStatus())) {
                        i=i+1;
                    }else{
                        iterator.remove();//使用迭代器的删除方法删除
                    }
                }
            }
            s.setAllGrade(i);
        }
        return new PageInfo<>(l);
    }



    @Override
    public int addStudent(student s) {
        //检测学号是否存在
        student ss= studentMapper.findStudentByStudentNo(s.getStudentNo());
        if(ss!=null){//不为空说明存在，返回0,表示注册失败
            return 0;
        }
        int i=studentMapper.addStudent(s);
        return  i;
    }

}
