package com.hqbx.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hqbx.model.*;
import com.hqbx.service.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AddController {
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
    @RequestMapping("Insertxsxxgl")
    public String InsertSfkyxxgl(@RequestParam(value = "sid") int sid,
                                 @RequestParam(value = "sname") String sname,
                                 @RequestParam(value = "sschool") String sschool,
                                 @RequestParam(value = "smajor") String smajor,
                                 @RequestParam(value = "sclasses") String sclasses,
                                 HttpServletRequest httpServletRequest){
        if (studentService.getStudentByid(sid)!=null)
        {
            return "学号已存在！";
        }
        Student student = new Student();
        student.setClasses(sclasses);
        student.setMajor(smajor);
        student.setSchool(sschool);
        student.setUname(sname);
        student.setId(sid);
        if(studentService.insertStudent(student)!=0) {
            setLog.setlod(httpServletRequest, "添加学号为"+sid+"的学生信息");
            return "添加成功，请关闭窗口";
        }
        else
            return "添加失败，请联系管理员";
    }

    @RequestMapping("Insertjsxxgl")
    public String Insertjsxxgl(@RequestParam(value = "tid") int tid,
                                 @RequestParam(value = "tname") String tname,
                                 @RequestParam(value = "tschool") String tschool,
                                 @RequestParam(value = "zyzc") String zyzc,
                                 HttpServletRequest httpServletRequest){
        if (teacherService.getTeacherById(tid)!=null)
        {
            return "工号已存在！";
        }
        Teacher teacher = new Teacher();
        teacher.setZyzc(zyzc);
        teacher.setSchool(tschool);
        teacher.setUname(tname);
        teacher.setId(tid);
        if(teacherService.insertTeacher(teacher)!=0) {
            setLog.setlod(httpServletRequest, "添加工号为"+tid+"的教师信息");
            return "添加成功，请关闭窗口";
        }
        else
            return "添加失败，请联系管理员";
    }

    @RequestMapping("Insertwxgzhgl")
    public String Insertwxgzhgl(@RequestParam(value = "mid") int mid,
                               @RequestParam(value = "mname") String mname,
                               HttpServletRequest httpServletRequest){
        if (maintainerService.getMaintainerById(mid)!=null&&maintainerService.getMaintainerByusername(mname)!=null)
        {
            return "工号已存在！或用户名已存在";
        }
        Maintainer maintainer = new Maintainer();
        maintainer.setId(mid);
        maintainer.setUname(mname);
        maintainer.setPass(UtilPacket.Md5MD5String("123456"));
        if(maintainerService.insertMaintainer(maintainer)!=0) {
            setLog.setlod(httpServletRequest, "添加工号为"+mid+"的维修工账号");
            return "添加成功，请关闭窗口";
        }
        else
            return "添加失败，请联系管理员";
    }

    @RequestMapping("Insertglyzhgl")
    public String Insertglyzhgl(@RequestParam(value = "mid") int mid,
                                @RequestParam(value = "mname") String mname,
                                HttpServletRequest httpServletRequest){
        if (adminService.getAdminById(mid)!=null&&adminService.getAdminByUname(mname)!=null)
        {
            return "工号已存在！或用户名已存在";
        }
        Admin admin = new Admin();
        admin.setId(mid);
        admin.setUname(mname);
        admin.setPass(UtilPacket.Md5MD5String("123456"));
        if(adminService.insertAdmin(admin)!=0) {
            setLog.setlod(httpServletRequest, "添加工号为"+mid+"的管理员账号");
            return "添加成功，请关闭窗口";
        }
        else
            return "添加失败，请联系管理员";
    }

}
