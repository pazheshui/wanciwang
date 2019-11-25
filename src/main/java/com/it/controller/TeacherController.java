package com.it.controller;


import com.github.pagehelper.PageInfo;
import com.it.Util.AboutFile;
import com.it.Util.JwtUtil;
import com.it.javabean.FileInfo;
import com.it.model.score;
import com.it.model.student;
import com.it.model.task;
import com.it.model.teacher;
import com.it.service.ScoreService;
import com.it.service.StudentService;
import com.it.service.TaskService;
import com.it.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ScoreService scoreService;
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String, Object> login(String teacherNo,String passWord,HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        teacher t=teacherService.teacherLogin(teacherNo,passWord);
        if (t!=null){//查到用户存在，签发一个令牌给该用户，用户后续带着令牌访问其他请求
            String token = JwtUtil.sign(teacherNo,passWord);
            if (token != null){
                map.put("code", "200");
                map.put("message","认证成功");
                map.put("token", token);
                map.put("id",teacherNo);//id也就是教师id，其实从前端输入框拿就可以了
                return map;
            }
        }
        map.put("code", "500");
        map.put("message","登录失败");
        return map;
    }
    @RequestMapping(value = "/api/teacher/getTeacherByNo",method = RequestMethod.POST)
    public teacher getTeacherByNo(String id) {
        teacher t=teacherService.findTeaByNo(id);
        return t;
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String downLoad(HttpServletResponse response,String name){
        System.out.println("文件名是"+name);
        String filename="a.txt";
        String filePath = "C:/Users/Administrator/Desktop/test" ;
        File file = new File(filePath + "/" + filename);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
//    @RequestMapping(value = "/testFile",method = RequestMethod.POST)
//    public void testFile(MultipartFile fileName, HttpServletRequest httpServletRequest){
//        System.out.println(fileName.getOriginalFilename());
//        System.out.println(httpServletRequest.getParameter("userName"));
//    }
    @RequestMapping(value = "/api/teacher/releaseTask",method = RequestMethod.POST)
    public void releaseTask(task t,String[] selectedStudentTd,MultipartFile[] files){
        System.out.println("发布任务了");
        taskService.addTask(t,selectedStudentTd,files);
//        String fileSavePath="C:\\Users\\Administrator\\Desktop\\tea\\;
    }
//    @RequestMapping(value = "/api/teacher/SelectTask",method = RequestMethod.POST)
//    public PageInfo<task> SelectTask(@RequestParam(defaultValue = "1",required = false)int pageIndex, @RequestParam(defaultValue = "3",required = false)int pageSize, String teacherNo){
//        PageInfo<task> l=taskService.findTasksByTeacherNo(pageIndex,pageSize,teacherNo);
//        return l;
//    }
    @RequestMapping(value = "/api/teacher/LookTaskNoPage",method = RequestMethod.POST)
    public Map<String,Object> LookTaskNoPage(String teacherNo){
        Map<String,Object> map = new HashMap<>();
        List<task> l=taskService.findTasksByTeacherNoPage(teacherNo);
        map.put("code",200);
        map.put("data",l);
        return map;
    }
    @RequestMapping(value = "/api/teacher/findScoreByTaskId",method = RequestMethod.POST)
    public Map<String,Object> findScoreByTaskId(String taskId){
        Map<String,Object> map = new HashMap<>();
        List<score> l=scoreService.findScoreByTaskId2(taskId);
        map.put("code",200);
        map.put("data",l);
        return map;
    }
    @RequestMapping(value = "/api/teacher/AddStudent",method = RequestMethod.POST)
    public Map<String,Object> AddStudent(student s){
        Map<String, Object> map = new HashMap<>();
        int i=studentService.addStudent(s);
        if(i>0){
            map.put("code", "200");
            map.put("message","注册成功");
        }else{
            map.put("code", "500");
            map.put("message","注册失败，学号重复");
        }
        return map;
    }
    @RequestMapping(value = "/api/teacher/findStudentByTeacherId",method = RequestMethod.POST)
    public Map<String,Object> SelectStudent(String teacherId){//学生信息包括平均分、接受任务个数等
        Map<String,Object> map = new HashMap<>();
        List<student> l=studentService.findStudentByTeacherId(teacherId);
        System.out.println(l);
        map.put("code",200);
        map.put("data",l);
        return map;
    }
    @RequestMapping(value = "/api/teacher/updateTaskById",method = RequestMethod.POST)
    public Map<String,Object> updateTaskById(task t,@RequestParam(value = "stuList[]",required = false)String[] stuList){
        task oldTask=taskService.findOneTasksById(t.getId());
        String oldExplainFile=oldTask.getExplainFile();
        int res=taskService.updateTaskById(t,stuList,oldExplainFile);
        Map<String,Object>  m=new HashMap<>();
        if(res>0){
            m.put("code","200");
        }else{
            m.put("code","500");
        }
        return m;
    }
    @RequestMapping(value = "/api/teacher/deleteTaskById",method = RequestMethod.POST)
    public Map<String,Object> deleteTaskById(task t){
        int res=taskService.deleteTaskById(t);
        Map<String,Object>  m=new HashMap<>();
        if(res>0){
            m.put("code","200");
        }else{
            m.put("code","500");
        }
        return m;
    }
    @RequestMapping(value = "/api/teacher/updateScoreStatusById",method = RequestMethod.POST)
    public Map<String,Object> updateScoreStatusById(score s){
        int res=scoreService.updateScoreStatusById(s);
        Map<String,Object>  m=new HashMap<>();
        if(res>0){
            m.put("code","200");
        }else{
            m.put("code","500");
        }
        return m;
    }
    //下载教师发布任务时添加的附件，标志
    @RequestMapping(value = "/api/tea/fileDownload",method = RequestMethod.GET)
    public String stuFileDownload(HttpServletResponse response,String name,String realName){
//        String filePath = "C:/Users/Administrator/Desktop/tea" ;
        String filePath="C:/projectSource/zuoyebang/tea";
        try {
            AboutFile.fileDownload(response,name,filePath,realName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    //修改任务添加额外的学生,teacherNo获取名下所有学生，taskId获取任务下学生
    @RequestMapping(value = "/api/teacher/updateTaskGetStu",method = RequestMethod.POST)
    public Map<String,Object> updateTaskGetStu(String teacherNo,String taskId){

        List<student> l=studentService.updateTaskGetStu(teacherNo,taskId);
        Map<String,Object>  m=new HashMap<>();
        if(l.size()>0){
            m.put("code","200");
            m.put("data",l);
        }else{
            m.put("code","500");
            m.put("data",l);
        }
        return m;
    }
    @RequestMapping(value = "/api/teacher/getTaskWithPage",method = RequestMethod.POST)
    public Map<String,Object> getTaskWithPage(String teacherNo,int page,int limit){

        PageInfo<task> l=taskService.getTaskWithPage(teacherNo,page,limit);
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
    @RequestMapping(value = "/api/teacher/getStuWithPage",method = RequestMethod.POST)
    public Map<String,Object> getStuWithPage(String teacherId,int page,int limit){
        PageInfo<student> l=studentService.getStuWithPage(teacherId,page,limit);
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
    @RequestMapping(value = "/api/teacher/registerTeacher",method = RequestMethod.POST)
    public Map<String,Object> registerTeacher(teacher t){
        Map<String, Object> map = new HashMap<>();
        teacher t1=teacherService.findTeaByNo(t.getTeacherNo());
        if(t1!=null){
            map.put("code","500");
            map.put("msg","注册失败，工号重复");
            return map;
        }
        teacher t2=teacherService.findTeaByTeacherName(t.getTeacherName());
        if(t2!=null){
            map.put("code","500");
            map.put("msg","注册失败，用户名重复");
            return map;
        }
        //注册
        int i=teacherService.addTeacher(t);
        if(i>0){
            map.put("code","200");
            map.put("msg","注册成功");
        }else{
            map.put("code","500");
            map.put("msg","注册失败，发生错误");
        }
        return map;
    }

    @RequestMapping(value = "/api/teacher/deleteScoreById",method = RequestMethod.POST)
    public Map<String,Object> deleteScoreById(int id){
        int res=scoreService.deleteScoreById(id);
        Map<String,Object>  m=new HashMap<>();
        if(res>0){
            m.put("code","200");
        }else{
            m.put("code","500");
        }
        return m;
    }

    @RequestMapping(value = "/api/teacher/deleteScoreByStudentNo",method = RequestMethod.POST)
    public Map<String,Object> deleteScoreByStudentNo(student s){
        int res=studentService.deleteStudentByStudentNo(s);
        int res2=scoreService.deleteScoreByStudentNo(s.getStudentNo());
        Map<String,Object>  m=new HashMap<>();
        if(res>0){
            m.put("code","200");
        }else{
            m.put("code","500");
        }
        return m;
    }
//教师修改任务时，文件上传的接口，标志
    @RequestMapping(value = "/api/tea/fileUpload",method = RequestMethod.POST)
    public Map<String,Object> homeWorkSubmit(MultipartFile file){
        Map<String,Object> map = new HashMap<>();

//        String fileSavePath="C:\\Users\\Administrator\\Desktop\\tea\\";
        String fileSavePath="C:/projectSource/zuoyebang/tea";
        FileInfo fileInfo=AboutFile.fileUpload(fileSavePath,file);
        if(fileInfo!=null){
            map.put("code",0);
            map.put("data",fileInfo);
        }
        return map;
    }
    @RequestMapping(value = "/api/teacher/deleteTaskFile",method = RequestMethod.POST)
    public Map<String,Object> deleteTaskFile(String taskId,String uuidName){
        task t=taskService.findOneTasksById(Integer.parseInt(taskId));//获得那条任务
        int i=taskService.updateTaskExplainFile(t,uuidName);
        Map<String,Object>  m=new HashMap<>();
        if(i==1){
            m.put("code","200");
        }else{
            m.put("code","500");
        }
        return m;
    }
}
