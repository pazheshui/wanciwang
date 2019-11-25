package com.it.service.imp;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.it.Util.UUidUtil;
import com.it.javabean.FileInfo;
import com.it.mapper.ScoreMapper;
import com.it.mapper.TaskMapper;
import com.it.model.score;
import com.it.model.task;
import com.it.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class TaskServiceImp implements TaskService{
    @Autowired//任务表
    private TaskMapper taskMapper;
    @Autowired//分数表
    private ScoreMapper scoreMapper;

    @Override//标志
    public int addTask(task t, String[] selectedStudentTd, MultipartFile[] files) {
//        String fileSavePath="C:\\Users\\Administrator\\Desktop\\tea\\";
        String fileSavePath="C:/projectSource/zuoyebang/tea";
        Gson g=new Gson();
        List<FileInfo> l=new ArrayList();
        String explainFile="";
        for(int i=0;i<files.length;i++) {//遍历文件列表。上传
            String oName=files[i].getOriginalFilename();
            String suffix=oName.split("\\.")[1];//后缀
            String uuidName= UUidUtil.getUUID();
            //上传
            File newFile=new File(fileSavePath,uuidName+"."+suffix);
            try {
                files[i].transferTo(newFile);
            } catch (IOException e) {
                System.out.println("上传出错");
                e.printStackTrace();
            }
            //保存文件信息到一个列表，后面存到数据库
            FileInfo f=new FileInfo(oName,uuidName+"."+suffix);
            l.add(f);
        }
        if(l.size()>0){
            explainFile=g.toJson(l);
        }else{
            explainFile="[]";//没有文件，给他一个空文件列表
        }

        //操作task表
        t.setCreateTime(new Date());
        t.setExplainFile(explainFile);
        System.out.println("task is"+t);
        int i1=taskMapper.addTask(t);
        int taskId=t.getId();//获得插入后的任务id
        System.out.println("newIndex is"+taskId );
        //操作score表
        int i2=0;
        if(selectedStudentTd!=null){
            for(int j=0;j<selectedStudentTd.length;j++){
                score s=new score();
                s.setStudentNo(selectedStudentTd[j]);
                s.setTaskId(taskId+"");
                i2=i2+scoreMapper.addScore(s);
                System.out.println("insert one to score");
            }
        }

        return 1;
    }

//    @Override//废弃
//    public PageInfo<task> findTasksByTeacherNo(int pageIndex, int pageSize, String teacherNo) {
//        PageHelper.startPage(pageIndex,pageSize);//先根据教师id查到该教师发布的任务
//        List<task> l=taskMapper.findTasksByTeacherNo(teacherNo);
//        if(l!=null){//不为空，查询附加的数据，为空返回空
//            for(task t:l){//针对每个任务，根据任务id，查询有哪些学生接受该任务
//                int id=t.getId();//任务的id，到score表查
//                List<score> ss=scoreMapper.findScoresByTaskId(id+"");
//                t.setScores(ss);
//            }
//            PageInfo<task> pageInfo=new PageInfo<>(l);
//            return pageInfo;
//        }
//        return null;
//    }

    @Override
    public List<task> findTasksByTeacherNoPage(String teacherNo) {
        List<task>l =taskMapper.findTasksByTeacherNo(teacherNo);
        return l;
    }

    @Override
    public int updateTaskById(task t,String[] stuList,String oldExplainFile) {
        String accessExplain=t.getExplainFile();
        if(!accessExplain.equals("[]")){//如果附加的文件不为空，则添加到旧的json数组后面
            Type type = new TypeToken<List<FileInfo>>() {}.getType();
            List<FileInfo> oldfileLists = new Gson().fromJson(oldExplainFile, type);

            List<FileInfo> accessfileLists = new Gson().fromJson(accessExplain, type);

            for(FileInfo f:accessfileLists){//将接受的文件列表中的每一项，附加到原来的数组中
                oldfileLists.add(f);
            }
            //然后再转为字符串
            String finalString=new Gson().toJson(oldfileLists);
            t.setExplainFile(finalString);
        }else{//假如接受文件为空，我们让他保持之前的文件数据
            t.setExplainFile(oldExplainFile);
        }
        int i=taskMapper.updateTaskById(t);//修改任务标题 内容
        //根据任务id 学生学号 插入成绩表
        if(stuList!=null){
            for(int j=0;j<stuList.length;j++){
                score sco=new score();
                String taId=t.getId()+"";
                String stNo=stuList[j];
                sco.setTaskId(taId);
                sco.setStudentNo(stNo);
                scoreMapper.addScore(sco);
            }
        }
        return i;
    }

    @Override
    public int deleteTaskById(task t) {
        int i=taskMapper.deleteTaskById(t);
        int j=scoreMapper.deleteScoreByTaskId(t.getId()+"");
        return i+j;
    }

    @Override
    public PageInfo<task> getTaskWithPage(String teacherNo,int page,int limit) {
        PageHelper.startPage(page,limit);
        List<task>l =taskMapper.findTasksByTeacherNo(teacherNo);
        return new PageInfo<>(l);
    }

    @Override
    public task findOneTasksById(int id) {
        return taskMapper.findOneTasksById(id);
    }

    @Override
    public int updateTaskExplainFile(task t,String explainFile) {
        int off=0;
        //将字符串转为[{}]
        String oldFile=t.getExplainFile();
        Type type = new TypeToken<List<FileInfo>>() {}.getType();
        List<FileInfo> fileLists = new Gson().fromJson(oldFile, type);
           //遍历每个对象的uuidName属性，跟explainFile进行对比，找到了就删除那个对象
        Iterator<FileInfo> iterator = fileLists.iterator();
        System.out.println("fileLists修改前的长度是"+fileLists.size());
        while (iterator.hasNext()) {
            FileInfo oneF = iterator.next();
            String oneFuuidName=oneF.getUuidName();
            if(oneFuuidName.equals(explainFile)){
                iterator.remove();
                off=1;
                break;
            }
        }
        System.out.println("fileLists修改后的长度是"+fileLists.size());
        //将修改后的结果更新到数据库中去
        String newString=new Gson().toJson(fileLists);
        t.setExplainFile(newString);
        taskMapper.updateTaskExplainFile(t);
        return off;
    }

}


