package com.hqbx.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hqbx.model.*;
import com.hqbx.service.BxformService;
import com.hqbx.service.StudentService;
import com.hqbx.service.TeacherService;
import com.hqbx.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
public class WxController {
    @Reference
    private UserService userService;
    @Reference
    private TeacherService teacherService;
    @Reference
    private StudentService studentService;
    @Reference
    private BxformService bxformService;

    @RequestMapping("/vxlogin")
    public Map<String, Object> vxlogin (@RequestParam("code")String code){

        List<Map<String, Object>> list = new ArrayList<>();
        utilpack utilpackd = new utilpack();
        String appid = "wxa373c8c7e0c19c04";
        String key = "a6b6e93d27de45260480a8e137943490";
        String openid = utilpackd.getopenid(appid,key,code);
        User user = userService.getUserByOpenid(openid);

        if (user==null){

            User newuser=new User();
            newuser.setCode(5);
            newuser.setOpenid(openid);
            if (userService.insertUser(newuser)!=0){
                String msg="注册成功！";
                int msgcode = 0;
                User user1 = userService.getUserByOpenid(openid);
                Map map = new HashMap();
                map.put("id", user1.getId());
                map.put("code",user1.getCode());
                map.put("openid",user1.getOpenid());
                list.add(map);
                Map<String, Object> map1 = new HashMap<>();
                map1.put("msg",msg);
                map1.put("msgcode",msgcode);
                map1.put("data",list);
                setLog.setlod(500, "用户注册成功openid为："+openid);
                return map1;
            }
            String msg="注册失败！";
            int msgcode = 400;
            Map<String, Object> map1 = new HashMap<>();
            map1.put("msg",msg);
            map1.put("msgcode",msgcode);
            map1.put("data",null);
            return map1;
        }
        else {
            String msg="登录成功！";
            int msgcode = 0;
            Map map = new HashMap();
            map.put("id", user.getId());
            map.put("code",user.getCode());
            map.put("tel",user.getTel());
            map.put("uid",user.getVxid());
            map.put("openid",user.getOpenid());
            list.add(map);
            Map<String, Object> map1 = new HashMap<>();
            map1.put("msg",msg);
            map1.put("msgcode",msgcode);
            map1.put("data",list);
            setLog.setlod(0, user.getId()+"用户登录");
            return map1;
        }
    }

    @RequestMapping("/getuserinfo")
    public Map<String, Object> selectxsxxgl(@RequestParam("uid")int uid,@RequestParam("code")int code) {
        if (code==2){
            List<Map<String, Object>> list = new ArrayList<>();
            Teacher teacher = teacherService.getTeacherById(uid);
                    int id = teacher.getId();
                    String uname = teacher.getUname();
                    String zyzc = teacher.getZyzc();
                    String school = teacher.getSchool();
                    Map map = new HashMap();
                    map.put("id", id);
                    map.put("uname", uname);
                    map.put("zyzc", zyzc);
                    map.put("school", school);
                    list.add(map);
            Map<String, Object> map1 = new HashMap<>();
            map1.put("code", 0);
            map1.put("msg", "ok");
            map1.put("count", list.size());
            map1.put("data", list);
            return map1;
        }
        if (code==1){
            List<Map<String, Object>> list = new ArrayList<>();
            Student student = studentService.getStudentByid(uid);
            int id = student.getId();
            String uname = student.getUname();
            String zyzc = student.getClasses();
            String school = student.getSchool();
            String major = student.getMajor();
            Map map = new HashMap();
            map.put("id", id);
            map.put("uname", uname);
            map.put("zyzc", zyzc);
            map.put("school", school);
            map.put("major", major);
            list.add(map);
            Map<String, Object> map1 = new HashMap<>();
            map1.put("code", 0);
            map1.put("msg", "ok");
            map1.put("count", list.size());
            map1.put("data", list);
            return map1;
        }
        else {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("code", 400);
            map1.put("msg", "对不起您非本校师生！");
            return map1;
        }
    }

    @RequestMapping("/getuserByopenid")
    public Map<String, Object> getuserByopenid(@RequestParam String openid) {
            User user = userService.getUserByOpenid(openid);
            List list = new ArrayList();
            if (user!=null) {
                list.add(user);
            }
            Map<String, Object> map1 = new HashMap<>();
            map1.put("code", 0);
            map1.put("msg", "ok");
            map1.put("count", list.size());
            map1.put("data", list);
            return map1;
    }

    @RequestMapping("/getbxform")
    public Map<String, Object> getbxform(@RequestParam("uid")int uid) {
        List<Bxform> bxforms = bxformService.getBxformByuid(uid);
        List<Map<String, Object>> list = new ArrayList<>();
        if (bxforms != null) {
            for (Bxform bxform : bxforms) {
                int id = bxform.getId();
                String address = bxform.getAddress();
                String bxlx = bxform.getBxlx();
                String img = bxform.getImg();
                String info = bxform.getInfo();
                Date time = bxform.getTime();
                int ztid = bxform.getZtid();
                String ztstr= "不可预料的错误！";
                //0未处理1已分配维修工2维修工已确认3订单完成
                if (ztid==0){
                    ztstr="未处理";
                }
                if (ztid==1){
                    ztstr="已分配维修工";
                }
                if (ztid==2){
                    ztstr="维修工已确认";
                }
                if (ztid==3){
                    ztstr="订单完成";
                }
                Map map = new HashMap();
                map.put("id", id);
                map.put("address", address);
                map.put("bxlx", bxlx);
                map.put("img", img);
                map.put("info", info);
                map.put("time", time);
                map.put("ztid", ztstr);
                list.add(map);
            }
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("code", 0);
        map1.put("msg", "ok");
        map1.put("count", list.size());
        map1.put("data", list);
        return map1;
    }

    @RequestMapping("/upuserinfo")
    public boolean upgdgl(@RequestParam(value = "id") int id,
                          @RequestParam(value = "tel") String tel) {
        User user = userService.getUserById(id);
        if (user != null) {
            user.setTel(tel);
            if (userService.upUser(user)!=0)
                return true;
        }
        return false;
    }

    @RequestMapping("/renzheng")
    public boolean renzheng(@RequestParam(value = "id") int id,
                          @RequestParam(value = "code") int code,
                            @RequestParam(value = "uname") String uname,
                            @RequestParam(value = "school") String school,
                            @RequestParam String xy,
                            @RequestParam String zy) {
        int haveuser=0;
        int uid = 0;
        if (code==2){
            List<Teacher> teachers = teacherService.getTeacherList();
            if (teachers!=null)
            for (Teacher teacher :teachers){
                String tname = teacher.getUname();
                if (tname.equals(uname))
                    if (teacher.getSchool().equals(school)) {
                        uid=teacher.getId();
                        haveuser = 1;
                    }
            }
        }
        if (code==1){
            List<Student> students = studentService.getStudentList();
            if (students!=null)
                for (Student student :students){
                    String tname = student.getUname();
                    if (tname.equals(uname))
                        if (student.getSchool().equals(school)) {
                            uid = student.getId();
                            haveuser = 1;
                        }
                }
        }
        if (haveuser==1){
            User user = userService.getUserById(id);
            if (user != null) {
                user.setCode(code);
                user.setVxid(uid);
                user.setXy(xy);
                user.setZy(zy);
                user.setSname(uname);
                if (userService.upUser(user)!=0)
                    return true;
            }
        }
        return false;
    }
    @RequestMapping("/insertbxform")
    public boolean insertbxform(@RequestParam(value = "uid") int uid,
                                @RequestParam(value = "info") String info,
                                @RequestParam(value = "address") String address,
                                @RequestParam(value = "img") String img,
                                @RequestParam(value = "bxlx") String bxlx) {
        Bxform bxform = new Bxform();
        Date date = new Date(new Date().getTime()+(8*3600*1000));
        bxform.setZtid(0);
        bxform.setAddress(address);
        bxform.setImg(img);
        bxform.setBxlx(bxlx);
        bxform.setInfo(info);
        bxform.setUid(uid);
        bxform.setTime(date);
        if (bxformService.insertBxform(bxform)!=0)
            return true;
        return false;
    }

    @RequestMapping("/wechatupload")
    public Object wechatupload(MultipartFile file, HttpServletRequest request) {
        try {
            String path = request.getSession().getServletContext().getRealPath("/upload/");
            String image = UtilPacket.uploadImage(file, path);
            if (image != null) {
                setLog.setlod(request, "上传了文件名为:" + image + "的图片，路径为:" + path + "，完整路径为:" + path+"/"+image);
                return image;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
