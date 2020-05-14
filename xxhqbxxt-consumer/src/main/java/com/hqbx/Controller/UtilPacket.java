package com.hqbx.Controller;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Date;

public class UtilPacket {
    //上传图片
    public static String uploadImage(MultipartFile file, String path) throws IOException {
        String name = file.getOriginalFilename() ;
        String suffixName = name.substring(name.lastIndexOf(".")) ;
        Date date = new Date() ;
        String hash = String.valueOf(date.getTime());//给照片自定义一个名字，用时间做名称，不会重复
        String fileName = hash + suffixName ;
        File tempFile = new File(path, fileName) ;
        if(!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs() ;
        }
        tempFile.createNewFile() ;
        file.transferTo(tempFile);
        return tempFile.getName() ;
    }
//返回两次md5加密结果
    public static String Md5MD5String(String old) {
        UtilPacket utilPacket = new UtilPacket();
        return utilPacket.getMd5String(utilPacket.getMd5String(old));
    }
    private String getMd5String(String str){
        try {
            //java自带工具包MessageDigest
            MessageDigest md5 = MessageDigest.getInstance("md5");
            //实现Base64的编码
            BASE64Encoder base64 = new BASE64Encoder();
            //进行加密
            String newStr = base64.encode(md5.digest(str.getBytes("utf-8")));
            return newStr;//返回加密后的字符
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
