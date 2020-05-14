package com.hqbx.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hqbx.model.Student;
import com.hqbx.model.Teacher;
import com.hqbx.model.User;
import com.hqbx.service.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DelController {
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
    @RequestMapping("/delxsxxgl")
    public boolean delyhxxgl(HttpServletRequest httpServletRequest, @RequestParam int id){
        Student student = studentService.getStudentByid(id);
        User user = userService.getUserByUid(id);
        if (user!=null){
           if (user.getCode() == 1){
                userService.delUser(user.getId());
           }
        }
        if (studentService.delStudentById(id)!=0) {
            {

                setLog.setlod(httpServletRequest, "删除了学号为"+id+"的学生信息");
                return true;
            }
        }
        return false;
    }

    @RequestMapping("/deljsxxgl")
    public boolean deljsxxgl(HttpServletRequest httpServletRequest, @RequestParam int id){
        Teacher teacher = teacherService.getTeacherById(id);
        User user = userService.getUserByUid(id);
        if (user!=null){
            if (user.getCode() == 2){
                userService.delUser(user.getId());
            }
        }
        if (teacherService.delTeacherById(id)!=0) {
            {

                setLog.setlod(httpServletRequest, "删除了工号为"+id+"的教师信息");
                return true;
            }
        }
        return false;
    }

    @RequestMapping("/delyhzhgl")
    public boolean delyhzhgl(HttpServletRequest httpServletRequest, @RequestParam int id){
        if (userService.delUser(id)!=0) {
            {
                setLog.setlod(httpServletRequest, "删除了主键为"+id+"的用户");
                return true;
            }
        }
        return false;
    }

    @RequestMapping("/delwxgzhgl")
    public boolean delwxgzhgl(HttpServletRequest httpServletRequest, @RequestParam int id){
        if (maintainerService.delMaintainerByid(id)!=0) {
            {
                setLog.setlod(httpServletRequest, "删除了工号为"+id+"的维修工账号");
                return true;
            }
        }
        return false;
    }

    @RequestMapping("/delglyzhgl")
    public boolean delglyzhgl(HttpServletRequest httpServletRequest, @RequestParam int id){
        if (maintainerService.delMaintainerByid(id)!=0) {
            {
                setLog.setlod(httpServletRequest, "删除了工号为"+id+"的管理员账号");
                return true;
            }
        }
        return false;
    }

    @RequestMapping("/delgdfp")
    public boolean delgdfp(HttpServletRequest httpServletRequest, @RequestParam int id){
        if (maintainerService.delMaintainerByid(id)!=0) {
            {
                setLog.setlod(httpServletRequest, "删除了工单id为"+id+"的工单数据");
                return true;
            }
        }
        return false;
    }
}
