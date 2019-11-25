package com.it.Util;

import com.it.javabean.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class AboutFile {
    public static FileInfo fileUpload(String path, MultipartFile f){
        String oName=f.getOriginalFilename();
        String suffix=oName.split("\\.")[1];//后缀
        String uuidName= UUidUtil.getUUID();
        //上传
        File newFile=new File(path,uuidName+"."+suffix);
        try {
            f.transferTo(newFile);
            return new FileInfo(oName,uuidName+"."+suffix);
        } catch (IOException e) {
            System.out.println("上传出错");
            e.printStackTrace();
        }
        return null;
    }
    public static String fileDownload(HttpServletResponse response,String fileName,String filePath,String realName ) throws UnsupportedEncodingException {
        File file = new File(filePath + "/" + fileName);
        System.out.println("最开始的名字是"+realName);
        String real=realName.replaceAll("\\+","%20");    //处理空格转为加号的问题
        real = URLEncoder.encode(real, "UTF-8");//

        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=\"" + real +"\"");
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
            System.out.println("----------file download " + real);
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
}
