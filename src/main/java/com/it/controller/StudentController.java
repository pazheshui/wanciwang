package com.it.controller;

import com.github.pagehelper.PageInfo;
import com.it.Util.AboutFile;
import com.it.Util.JwtUtil;
import com.it.Util.UploadToken;
import com.it.javabean.FileInfo;
import com.it.model.score;
import com.it.model.student;
import com.it.model.teacher;
import com.it.service.ScoreService;
import com.it.service.StudentService;
import com.it.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class StudentController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ScoreService scoreService;
    @RequestMapping(value = "/api/student/findStudentByTeacherId",method = RequestMethod.POST)
    public List<student> findStudentByTeacherId(String id) {

        List<student> l=studentService.findStudentByTeacherId(id);
        return l;
    }
    //文件上传不同角色调不同的controller
    @RequestMapping(value = "/stu/login",method = RequestMethod.POST)
    public Map<String, Object> login(student s) {
        Map<String, Object> map = new HashMap<>();
        student ss=studentService.studentLogin(s);
        if (ss!=null){//查到用户存在，签发一个令牌给该用户，用户后续带着令牌访问其他请
            String token = JwtUtil.sign(s.getStudentNo(),s.getPassWord());//返回学生的老师
            teacher t5=teacherService.findTeaByNo(ss.getTeacherId());
            String teacherName=t5.getTeacherName();
            if (token != null){
                map.put("code", "200");
                map.put("message","认证成功");
                map.put("userName",ss.getUserName());
                map.put("token", token);
                map.put("uploadToken", UploadToken.getToken());
                map.put("id",s.getStudentNo());//id也就是学生id，其实从前端输入框拿就可以了
                map.put("teacherName",teacherName);
                return map;

            }
        }else {
            map.put("code", "500");
            map.put("message","登录失败");
        }
        return map;
    }
    //查看任务
    @RequestMapping(value = "/api/stu/LookTask",method = RequestMethod.POST)
    public Map<String,Object> findScoreByTaskId(String studentNo){
        Map<String,Object> map = new HashMap<>();
        List<score> l=scoreService.findScoreByStudentNo(studentNo);
        map.put("code",200);
        map.put("data",l);
        return map;
    }
    //提交作业的文件上传，标志
    @RequestMapping(value = "/api/stu/homeWorkSubmit",method = RequestMethod.POST)
    public Map<String,Object> homeWorkSubmit(MultipartFile file){
        Map<String,Object> map = new HashMap<>();

//        String fileSavePath="C:\\Users\\Administrator\\Desktop\\stu\\";
        String fileSavePath="C:/projectSource/zuoyebang/stu";
        FileInfo fileInfo=AboutFile.fileUpload(fileSavePath,file);
        if(fileInfo!=null){
            map.put("code",0);
            map.put("data",fileInfo);
        }
        return map;
    }
    //name表示uuidName,realName是原文件名
    //文件下载,标志
    @RequestMapping(value = "/api/stu/fileDownload",method = RequestMethod.GET)
    public String stuFileDownload(HttpServletResponse response,String name,String realName){
//        String filePath="http://119.29.245.187:8037/fileData";
//        String filePath = "C:/Users/Administrator/Desktop/stu" ;
//        String filePath="http://119.29.245.187/C:/projectSource/zuoyebang/tea";
        String filePath="C:/projectSource/zuoyebang/stu";

        try {
            AboutFile.fileDownload(response,name,filePath,realName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping(value = "/api/stu/homeWork",method = RequestMethod.POST)
    public Map<String,Object> homeWork(score s){
        Map<String,Object> map = new HashMap<>();
        int i=scoreService.updateScoreById(s);
        if(i>0){
            map.put("code",200);
        }else{
            map.put("code",500);
        }
        return map;
    }
    @RequestMapping(value = "/api/stu/getGrade",method = RequestMethod.POST)
    public Map<String,Object> getGrade(String gradeId){
        Map<String,Object> map = new HashMap<>();
        score s=scoreService.getScoreById(Integer.parseInt(gradeId));
        if(s!=null){
            map.put("code","200");
            map.put("data",s);
        }else{
            map.put("code","500");
            map.put("data",s);
        }
        return map;
    }
    @RequestMapping(value = "/api/stu/getScoreWithPage",method = RequestMethod.POST)
    public Map<String,Object> getScoreWithPage(String studentNo,int page,int limit){
        PageInfo<score> l=scoreService.getScoreWithPage(studentNo,page,limit);
        Map<String,Object>  m=new HashMap<>();
        if(l.getList().size()>0){
            m.put("code",200);
            m.put("data",l);
            m.put("msg","分页请求成功");
        }else{
            m.put("code",200);
            m.put("data",l);
            m.put("msg","分页请求失败");
        }
        return m;
    }
    @RequestMapping(value = "/api/stu/updateStudentStudent",method = RequestMethod.POST)
    public Map<String,Object> updateStudentStudent(student s,String newPassword){
        Map<String,Object>  m=new HashMap<>();
        student s1=studentService.studentLogin(s);
        if(s1==null){//为空说明匹配不到，则旧密码错误
            m.put("code","500");
            m.put("msg","旧密码错误，再想想吧");
            return m;
        }
        //设置新的密码
        s.setPassWord(newPassword);
        int i=studentService.updateStudentPassWord(s);
        if(i>0){
            m.put("code","200");
            m.put("msg","修改成功");
        }else{
            m.put("code","500");
            m.put("msg","发生错误");
        }
        return m;
    }
}
