package com.hqbx.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hqbx.model.*;
import com.hqbx.service.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UpController {
    @Reference
    private AdminService adminService;
    @Reference
    private BxformService bxformService;
    @Reference
    private MaintainerService maintainerService;
    @Reference
    private MenuService menuService;
    @Reference
    private StudentService studentService;
    @Reference
    private TeacherService teacherService;
    @Reference
    private UserService userService;
    @Reference
    private GroupinfoService groupinfoService;

    @RequestMapping("/inituser")
    public boolean inituser(@RequestParam int id, @RequestParam int qx,HttpServletRequest httpServletRequest) {
        if (qx == 1){
            Admin admin = adminService.getAdminById(id);
            if (admin!=null){
                admin.setPass("123456");
                if (adminService.upAdmin(admin) != 0) {
                    setLog.setlod(httpServletRequest, "重置了" + id + "管理员的密码");
                    return true;
                }
            }
        }
        else if (qx==2){
            Maintainer maintainer = maintainerService.getMaintainerById(id);
            if (maintainer != null) {
                maintainer.setPass("123456");
                if (maintainerService.upMaintainer(maintainer) != 0) {
                    setLog.setlod(httpServletRequest, "重置了" + id + "维修工的密码");
                    return true;
                }
            }
        }

        return false;
    }

    @RequestMapping("/upxsxxgl")
    public boolean upxsxxgl(@RequestParam(value = "id") int id,
                            @RequestParam(value = "field") String field,
                            @RequestParam(value = "updateValue") String updateValue,
                            HttpServletRequest httpServletRequest) {
        Student student = studentService.getStudentByid(id);
        if (student != null) {
            if (field.equals("uname"))
                student.setUname(updateValue);
            if (field.equals("school"))
                student.setSchool(updateValue);
            if (field.equals("major"))
                student.setMajor(updateValue);
            if (field.equals("classes"))
                student.setClasses(updateValue);
            if (studentService.upStudent(student) != 0) {
                setLog.setlod(httpServletRequest, "修改了" + id + "学生的" + field + "为" + updateValue);
                return true;
            }
            return false;
        }
        return false;
    }

    @RequestMapping("/upjsxxgl")
    public boolean upjsxxgl(@RequestParam(value = "id") int id,
                            @RequestParam(value = "field") String field,
                            @RequestParam(value = "updateValue") String updateValue,
                            HttpServletRequest httpServletRequest) {
        Teacher teacher = teacherService.getTeacherById(id);
        if (teacher != null) {
            if (field.equals("uname"))
                teacher.setUname(updateValue);
            if (field.equals("school"))
                teacher.setSchool(updateValue);
            if (field.equals("zyzc"))
                teacher.setZyzc(updateValue);
            if (teacherService.upTeacher(teacher) != 0) {
                setLog.setlod(httpServletRequest, "修改了" + id + "教师的" + field + "为" + updateValue);
                return true;
            }
            return false;
        }
        return false;
    }

    @RequestMapping("/upgdfp")
    public boolean upgdfp(@RequestParam(value = "gdbh") int gdbh,
                          @RequestParam(value = "wxg") int wxg,
                          HttpServletRequest httpServletRequest) {
        Bxform bxform = bxformService.getBxformById(gdbh);
        if (bxform != null) {
            bxform.setMid(wxg);
            bxform.setZtid(1);
            if (bxformService.upBxform(bxform) != 0) {
                setLog.setlod(httpServletRequest, "把工单" + gdbh + "分配给了ID为" + wxg + "的维修工");
                return true;
            }
            return false;
        }
        return false;
    }

    @RequestMapping("/initgdfp")
    public boolean initgdfp(@RequestParam int id,
                          HttpServletRequest httpServletRequest) {
        Bxform bxform = bxformService.getBxformById(id);
        if (bxform != null) {
            if (bxformService.initBxform(bxform) != 0) {
                setLog.setlod(httpServletRequest, "重置工单" + id + "的分配信息");
                return true;
            }
            return false;
        }
        return false;
    }

    @RequestMapping("/upgdgl")
    public boolean upgdgl(@RequestParam(value = "id") int id,
                          @RequestParam(value = "cz") int cz,
                          HttpServletRequest httpServletRequest) {
        Bxform bxform = bxformService.getBxformById(id);
        if (bxform != null) {
            if (cz==1){
                bxform.setZtid(2);
                if (bxformService.upBxform(bxform) != 0) {
                    setLog.setlod(httpServletRequest, "维修工已确认分配工单" + id);
                    return true;
                }
            }
            if (cz==2){
                bxform.setZtid(3);
                if (bxformService.upBxform(bxform) != 0) {
                    setLog.setlod(httpServletRequest, "维修工已完成工单" + id);
                    return true;
                }
            }

            return false;
        }
        return false;
    }

    @RequestMapping("/upcdgl")
    public boolean upcdgl(@RequestParam(value = "id") int id,
                             @RequestParam(value = "field") String field,
                             @RequestParam(value = "updateValue") String updateValue,
                             HttpServletRequest httpServletRequest) {
        Menu menu = menuService.selectByPrimaryKey(id);
        if (menu != null) {
            if (field.equals("menuname"))
                menu.setMenuname(updateValue);
            if (field.equals("menulink"))
                menu.setMenulink(updateValue);
            if (menuService.updateByPrimaryKeySelective(menu) != 0) {
                setLog.setlod(httpServletRequest, "修改了" + id + "菜单的" + field + "为" + updateValue);
                return true;
            }
            return false;
        }
        return false;
    }
    //权限中心
    @RequestMapping("/upyhzqxgl")
    public boolean upyhzqxgl(@RequestParam(value = "id") String id,
                            @RequestParam(value = "checked") String checked,
                            HttpServletRequest httpServletRequest) {
        String ids[] = id.split("_");
        int fcdid=Integer.parseInt(ids[0]);
        int zid = Integer.parseInt(ids[2]);
        String info="增加";
        Groupinfo groupinfo = groupinfoService.selectByPrimaryKey(zid);
        if (groupinfo != null) {
            String qx = groupinfo.getQx();
            String qxs[] = {};
            if (qx!=null) {
                qxs = qx.split(",");
            }
            if (checked.equals("true"))
                //增加
            {  info="增加";
                String aaaa= Arrays.toString(qxs);
                if (aaaa.length()>2)
                    qx+=","+fcdid;
                else
                    qx+=fcdid;
            }
            if (checked.equals("false"))
            //减少
            {
                qx="";
                info="减少";
                int tem = 0;
                int i;
                for(i = 0; i<qxs.length; i++){
                    if(Integer.parseInt(qxs[i])==fcdid){
                        tem = i;
                    }
                }
                for (i = 0;i<qxs.length;i++)
                {
                    if (i==tem)
                        continue;
                    qx+=qxs[i]+",";
                }
                if (qx.length()>1)
                qx = qx.substring(0, qx.length() -1);
            }
            groupinfo.setQx(qx);
            if (groupinfoService.updateByPrimaryKeySelective(groupinfo) != 0) {
                setLog.setlod(httpServletRequest, "修改了id为" + zid + "用户组的权限," + info + "权限id" + fcdid);
                return true;
            }
            return false;
        }
        return false;
    }


    @RequestMapping("/uppass")
    public boolean uppass( @RequestParam(value = "newpass") String newpass,HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        UserInfo users = (UserInfo) session.getAttribute("islogin");
        int qx = users.getQx();
        int uid = users.getId();
        if (qx==1){
            Admin admin = adminService.getAdminById(uid);
            admin.setPass(newpass);
            if(adminService.upAdmin(admin)!=0) {
                setLog.setlod(httpServletRequest, users.getUsername() + "用户修改了密码");
                session.invalidate();
                return true;
            }
        }
        else{
            Maintainer maintainer = maintainerService.getMaintainerById(uid);
            maintainer.setPass(newpass);
            if(maintainerService.upMaintainer(maintainer)!=0) {
                setLog.setlod(httpServletRequest, users.getUsername() + "用户修改了密码");
                session.invalidate();
                return true;
            }
        }
        return false;
    }

//    // 上传文件会自动绑定到MultipartFile
//    @RequestMapping("/upload")
//    public Map upload(HttpServletRequest request,
//                      @RequestParam("description") String description,
//                      @RequestParam("file") MultipartFile file) throws Exception {
//        //接收参数description
//        System.out.println("description: " + description);
//        //如果文件不为空，写入上传路径
//        Map map = new HashMap();
//        if (!file.isEmpty()) {
//            //上传文件路径
//            String path = request.getServletContext().getRealPath("/upload/");
//            System.out.println("path = " + path);
//            //上传文件名
//            String filename = file.getOriginalFilename();
//            File filePath = new File(path, filename);
//            //判断路径是否存在，如果不存在就创建一个
//            if (!filePath.getParentFile().exists()) {
//                filePath.getParentFile().mkdirs();
//            }
//            //将上传文件保存到一个目标文件当中
//            file.transferTo(new File(path + File.separator + filename));
//            System.out.println(filename);
//            map.put("filename", filename);
//            map.put("filepath", path);
//            map.put("fullpath", path + File.separator + filename);
//        } else {
//            map.put("err", "文件为空或服务器错误");
//        }
//        return map;
//    }

    @RequestMapping("/layupload")
    public Object upload(MultipartFile file, HttpServletRequest request) {
        try {
            String path = request.getSession().getServletContext().getRealPath("/upload/");
//            String path = request.getServletContext().getRealPath("/uploadFile");
//            String path = "D:/java/jianzhi/jianzhi-consumer/src/main/resources/static/images/upload";
//            String path = "D:/java/jianzhi/jianzhi-consumer/target/classes/static/upload";
            String image = UtilPacket.uploadImage(file, path);
            if (image != null) {
                Map map = new HashMap();
                map.put("filenamed",image);
                map.put("path",path);
                map.put("code",0);
                map.put("msg","ok");
                setLog.setlod(request, "上传了文件名为:" + image + "的图片，路径为:" + path + "，完整路径为:" + path+"/"+image);
                return map;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
