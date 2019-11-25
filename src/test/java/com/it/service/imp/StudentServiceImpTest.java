package com.it.service.imp;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class StudentServiceImpTest {
    @Test
    public void gets() throws Exception {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(uuid);
    }
    /**
     * 获得指定数目的UUID
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number){
        if(number < 1){
            return null;
        }
        String[] retArray = new String[number];
        for(int i=0;i<number;i++){
            retArray[i] = getUUID();
        }
        return retArray;
    }

    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }
//    @Test
//    public void findStudentByTeacherId() throws Exception {
//        System.out.println("321");
//    }

}