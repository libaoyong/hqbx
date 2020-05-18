package com.hqbx.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hqbx.service.*;
import com.hqbx.model.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
public class SelectController {
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
    @Reference
    private  LogService logService;

    @RequestMapping("/selectxtrzgl")
    public Map<String, Object> selectxtrzgl(HttpServletRequest httpServletRequest, @RequestParam(value = "page") int page,@RequestParam(value = "limit") int limit) {
        page = page-1;
        int start = page*limit;
        int end = (page+1)*limit-1;
        List<Log> logs = logService.getlogList();
        int datacount = logs.size();
        int i = 0;
        List<Map<String, Object>> list = new ArrayList<>();
        if (logs != null) {
            for (Log log : logs) {
                if (i>=start&&i<=end){
                    int id = log.getId();
                    String cz = log.getCz();
                    int czr = log.getCzr();
                    Date time = log.getTime();
                    String czrstr ="System";
                    if (adminService.getAdminById(czr)!=null) {
                        czrstr = adminService.getAdminById(czr).getUname();
                    }
                    else if(maintainerService.getMaintainerById(czr)!=null){
                        czrstr = maintainerService.getMaintainerById(czr).getUname();
                    }
                    Map map = new HashMap();
                    map.put("id", id);
                    map.put("cz", cz);
                    map.put("czr", czrstr);
                    map.put("time", 1900+time.getYear()+"年"+time.getMonth()+"月"+time.getDate()+"日 "+time.getHours()+":"+time.getMinutes()+":"+time.getSeconds());
                    list.add(map);
                }
                i++;
            }
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("code", 0);
        map1.put("msg", "ok");
        map1.put("count", datacount);
        map1.put("data", list);
        setLog.setlod(httpServletRequest, "查询了全部系统日志信息数据");
        return map1;
    }

    @RequestMapping("/selectcdgl")
    public Map<String, Object> selectcdgl(HttpServletRequest httpServletRequest) {
        List<Menu> menus = menuService.getmenuList();
        List<Map<String, Object>> list = new ArrayList<>();
        if (menus != null) {
            for (Menu menu : menus) {
                int id = menu.getId();
                String fdm = null;
                if(menu.getFdm()!=null&&menu.getFdm()!="") {
                    fdm = menuService.selectByPrimaryKey(Integer.parseInt(menu.getFdm())).getMenuname();
                }
                String menulink = menu.getMenulink();
                String menuname = menu.getMenuname();

                Map map = new HashMap();
                map.put("id", id);
                map.put("fdm", fdm);
                map.put("menulink", menulink);
                map.put("menuname", menuname);
                list.add(map);
            }
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("code", 0);
        map1.put("msg", "ok");
        map1.put("count", list.size());
        map1.put("data", list);
        setLog.setlod(httpServletRequest, "查询了全部菜单信息数据");
        return map1;
    }

    @RequestMapping("/selectyhzqxgl")
    public Map<String, Object> selectyhzqxgl(HttpServletRequest httpServletRequest) {
        List<Groupinfo> groupinfos = groupinfoService.getgroupinfoList();
        List<Map<String, Object>> list = new ArrayList<>();
        if (groupinfos != null) {
            for (Groupinfo groupinfo : groupinfos) {
                int id = groupinfo.getId();
                String qx = groupinfo.getQx();
                String groupname = groupinfo.getGroupname();
                String qxs[] ={};
                Map map = new HashMap();
                map.put("id", id+"_a");
                map.put("field", id+"_a");
                map.put("title", groupname);
                if (qx!=null)
                    qxs = qx.split(",");
                Map child = selectfcd(httpServletRequest);
                List childdatas = (List) child.get("data");
                List childsmap = new ArrayList();
                for (int i = 0 ;i<childdatas.size();i++){
                    boolean sfxz = false;
                    Map childmap = (Map)childdatas.get(i);
                    int childid = Integer.parseInt(String.valueOf(childmap.get("id")));
                    String aaaa= Arrays.toString(qxs);
                    if (aaaa.length()>2&&qxs!=null) {
                        for (String qxbsstr : qxs) {
                            if (Integer.parseInt(qxbsstr) == childid) {
                                sfxz = true;
                                break;
                            }
                        }
                    }
                    String childmenuname = String.valueOf(childmap.get("menuname"));
                    childmap.put("id",childid+"_b_"+id);
                    childmap.put("title",childmenuname);
                    childmap.put("checked",sfxz);
                    childmap.put("field",childid+"_b_"+id);
                    childsmap.add(childmap);
                }
                map.put("children",childsmap);
                list.add(map);
            }
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("data", list);
        setLog.setlod(httpServletRequest, "查询了全部权限节点数据");
        return map1;
    }
    @RequestMapping("/selectfcd")
    public Map<String, Object> selectfcd(HttpServletRequest httpServletRequest) {
        List<Menu> menus = menuService.getfdmmenuList();
        List<Map<String, Object>> list = new ArrayList<>();
        if (menus != null) {
            for (Menu menu : menus) {
                int id = menu.getId();
                String menuname = menu.getMenuname();
                Map map = new HashMap();
                map.put("id", id);
                map.put("menuname", menuname);
                list.add(map);
            }
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("code", 0);
        map1.put("msg", "ok");
        map1.put("count", list.size());
        map1.put("data", list);
        setLog.setlod(httpServletRequest, "查询了父菜单信息数据");
        return map1;
    }
    @RequestMapping("/selectxsxxgl")
    public Map<String, Object> selectxsxxgl(HttpServletRequest httpServletRequest) {
        List<Student> students = studentService.getStudentList();
        List<Map<String, Object>> list = new ArrayList<>();
        if (students != null) {
            for (Student student : students) {
                int id = student.getId();
                String uname = student.getUname();
                String classes = student.getClasses();
                String major = student.getMajor();
                String school = student.getSchool();
                String wxdl = "未开通";
                User user =  userService.getUserByUid(id);
                if (user!=null&&user.getVxid()==1)
                    wxdl="已开通";
                Map map = new HashMap();
                map.put("id", id);
                map.put("uname", uname);
                map.put("classes", classes);
                map.put("major", major);
                map.put("school", school);
                map.put("wxdl", wxdl);
                list.add(map);
            }
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("code", 0);
        map1.put("msg", "ok");
        map1.put("count", list.size());
        map1.put("data", list);
        setLog.setlod(httpServletRequest, "查询了全部学生信息数据");
        return map1;
    }

    @RequestMapping("/selectjsxxgl")
    public Map<String, Object> selectjsxxgl(HttpServletRequest httpServletRequest) {
        List<Teacher> teachers = teacherService.getTeacherList();
        List<Map<String, Object>> list = new ArrayList<>();
        if (teachers != null) {
            for (Teacher teacher : teachers) {
                int id = teacher.getId();
                String uname = teacher.getUname();
                String zyzc = teacher.getZyzc();
                String school = teacher.getSchool();
                String wxdl = "未开通";
                User user =  userService.getUserByUid(id);
                if (user!=null&&user.getVxid()==2)
                    wxdl="已开通";
                Map map = new HashMap();
                map.put("id", id);
                map.put("uname", uname);
                map.put("zyzc", zyzc);
                map.put("school", school);
                map.put("wxdl", wxdl);
                list.add(map);
            }
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("code", 0);
        map1.put("msg", "ok");
        map1.put("count", list.size());
        map1.put("data", list);
        setLog.setlod(httpServletRequest, "查询了全部教师信息数据");
        return map1;
    }

    @RequestMapping("/selectyhzhgl")
    public Map<String, Object> selectyhzhgl(HttpServletRequest httpServletRequest) {
        List<User> users = userService.getUserList();
        List<Map<String, Object>> list = new ArrayList<>();
        if (users != null) {
            for (User user : users) {
                Integer id = user.getId();
                Integer vxid = user.getVxid();
                Integer code = user.getCode();
                String openid = user.getOpenid();
                String tel = user.getTel();
                String sname = user.getSname();
                String xy = user.getXy();
                String zy = user.getZy();
                String codestr = "错误！";
                if (code==0){
                    codestr="不是本校生或教职工";
                }
                if (code==1){
                    codestr="本校学生";
                }
                if (code==2){
                    codestr="本校教职工";
                }
                if (code==5){
                    codestr="用户未认证";
                }
                Map map = new HashMap();
                map.put("id", id);
                map.put("vxid", vxid);
                map.put("code", codestr);
                map.put("openid", openid);
                map.put("tel", tel);
                map.put("sname", sname);
                map.put("xy", xy);
                map.put("zy", zy);
                list.add(map);
            }
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("code", 0);
        map1.put("msg", "ok");
        map1.put("count", list.size());
        map1.put("data", list);
        setLog.setlod(httpServletRequest, "查询了全部用户账号信息数据");
        return map1;
    }

    @RequestMapping("/selectwxgzhgl")
    public Map<String, Object> selectwxgzhgl(HttpServletRequest httpServletRequest) {
        List<Maintainer> maintainers = maintainerService.getMaintainerList();
        List<Map<String, Object>> list = new ArrayList<>();
        if (maintainers != null) {
            for (Maintainer maintainer : maintainers) {
                int id = maintainer.getId();
                String uname = maintainer.getUname();
                Map map = new HashMap();
                map.put("id", id);
                map.put("uname", uname);
                list.add(map);
            }
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("code", 0);
        map1.put("msg", "ok");
        map1.put("count", list.size());
        map1.put("data", list);
        setLog.setlod(httpServletRequest, "查询了全部维修工账号信息数据");
        return map1;
    }

    @RequestMapping("/selectglyzhgl")
    public Map<String, Object> selectglyzhgl(HttpServletRequest httpServletRequest) {
        List<Admin> admins = adminService.getAdminList();
        List<Map<String, Object>> list = new ArrayList<>();
        if (admins != null) {
            for (Admin admin : admins) {
                int id = admin.getId();
                String uname = admin.getUname();
                Map map = new HashMap();
                map.put("id", id);
                map.put("uname", uname);
                list.add(map);
            }
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("code", 0);
        map1.put("msg", "ok");
        map1.put("count", list.size());
        map1.put("data", list);
        setLog.setlod(httpServletRequest, "查询了全部管理员账号信息数据");
        return map1;
    }

    @RequestMapping("/selectgdfp")
    public Map<String, Object> selectgdfp(HttpServletRequest httpServletRequest) {
        List<Bxform> bxforms = bxformService.getBxformList();
        List<Map<String, Object>> list = new ArrayList<>();
        if (bxforms != null) {
            for (Bxform bxform : bxforms) {
                int id = bxform.getId();
                int uid = bxform.getUid();
                int ztid = bxform.getZtid();
                String address = bxform.getAddress();
                String bxlx = bxform.getBxlx();
                String img = bxform.getImg();
                int mid = bxform.getMid();
                Date time = bxform.getTime();
                String uidstr = "查无此人";
                String ztstr = "状态服务错误";
                int uidcode = userService.getUserByUid(uid).getCode();
                //1学生2教师
                if (uidcode==2){
                    uidstr = teacherService.getTeacherById(uid).getUname();
                }
                if (uidcode==1){
                    uidstr = studentService.getStudentByid(uid).getUname();
                }
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
                String midstr = maintainerService.getMaintainerById(mid).getUname();
                Map map = new HashMap();
                map.put("id", id);
                map.put("uid", uidstr);
                map.put("ztid", ztstr);
                map.put("address", address);
                map.put("bxlx", bxlx);
                map.put("img", img);
                map.put("mid", midstr);
                map.put("time",1900+time.getYear()+"年"+time.getMonth()+"月"+time.getDate()+"日 "+time.getHours()+":"+time.getMinutes()+":"+time.getSeconds());
                list.add(map);
            }
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("code", 0);
        map1.put("msg", "ok");
        map1.put("count", list.size());
        map1.put("data", list);
        setLog.setlod(httpServletRequest, "查询了全部工单信息数据");
        return map1;
    }


    @RequestMapping("/selectgdfpBywxg")
    public Map<String, Object> selectgdfpBywxg(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        UserInfo userInfo  = (UserInfo) session.getAttribute("islogin");
        List<Map<String, Object>> list = new ArrayList<>();
        if (userInfo!=null) {
            Integer wxgid = userInfo.getId();
            List<Bxform> bxforms = bxformService.getBxformBymid(wxgid);
            if (bxforms != null) {
                for (Bxform bxform : bxforms) {
                    int id = bxform.getId();
                    int uid = bxform.getUid();
                    int ztid = bxform.getZtid();
                    String address = bxform.getAddress();
                    String bxlx = bxform.getBxlx();
                    String img = bxform.getImg();
                    int mid = bxform.getMid();
                    Date time = bxform.getTime();
                    String uidstr = "查无此人";
                    String ztstr = "状态服务错误";
                    int uidcode = userService.getUserByUid(uid).getCode();
                    //1学生2教师
                    if (uidcode == 2) {
                        uidstr = teacherService.getTeacherById(uid).getUname();
                    }
                    if (uidcode == 1) {
                        uidstr = studentService.getStudentByid(uid).getUname();
                    }
                    //0未处理1已分配维修工2维修工已确认3订单完成
                    if (ztid == 0) {
                        ztstr = "未处理";
                    }
                    if (ztid == 1) {
                        ztstr = "已分配维修工";
                    }
                    if (ztid == 2) {
                        ztstr = "维修工已确认";
                    }
                    if (ztid == 3) {
                        ztstr = "订单完成";
                    }
                    String midstr = maintainerService.getMaintainerById(mid).getUname();
                    Map map = new HashMap();
                    map.put("id", id);
                    map.put("uid", uidstr);
                    map.put("ztid", ztstr);
                    map.put("address", address);
                    map.put("bxlx", bxlx);
                    map.put("img", img);
                    map.put("mid", midstr);
                    map.put("time", 1900 + time.getYear() + "年" + time.getMonth() + "月" + time.getDate() + "日 " + time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds());
                    list.add(map);
                }
            }
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("code", 0);
        map1.put("msg", "ok");
        map1.put("count", list.size());
        map1.put("data", list);
        setLog.setlod(httpServletRequest, "查询了全部工单信息数据");
        return map1;
    }


    @RequestMapping("/selectgdfpBywfp")
    public Map<String, Object> selectgdfpBywfp(HttpServletRequest httpServletRequest) {
        List<Bxform> bxforms = bxformService.getBxformList();
        List<Integer> list = new ArrayList<>();
        if (bxforms != null) {
            for (Bxform bxform : bxforms) {
                if (bxform.getMid()==null||bxform.getMid().equals("")){
                    int id = bxform.getId();
                    list.add(id);
                }
            }
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("success",true);
        map1.put("code",0);
        map1.put("msg", "ok");
        map1.put("data", list);
        setLog.setlod(httpServletRequest, "查询了全部未分配工单ID");
        return map1;
    }

    @RequestMapping("/selectgwxgId")
    public Map<String, Object> selectgwxgId(HttpServletRequest httpServletRequest) {
        List<Maintainer> maintainers = maintainerService.getMaintainerList();
        List<Map<String, Object>> list = new ArrayList<>();
        if (maintainers != null) {
            for (Maintainer maintainer : maintainers) {
                    int id = maintainer.getId();
                    String uname = maintainer.getUname();
                    Map map = new HashMap();
                    map.put("id", id);
                    map.put("uname", uname);
                    list.add(map);
            }
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("success",true);
        map1.put("code",0);
        map1.put("msg", "ok");
        map1.put("data", list);
        setLog.setlod(httpServletRequest, "查询了全部维修工ID和用户名");
        return map1;
    }


    @RequestMapping("/getoldpass")
    public Map<String,Object> getoldpass(@RequestParam(value = "oldpass")String oldpass,
                             @RequestParam(value = "newpass")String newpass,
                             @RequestParam(value = "newpass2")String newpass2,
                             HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        int code = 500;
        String msg= "账号服务器错误！";
        UserInfo users = (UserInfo) session.getAttribute("islogin");
        String upass = users.getPassword();
        setLog.setlod(httpServletRequest, "正在修改密码");
        if (upass.equals(oldpass)){
            if (newpass2.equals(newpass)){
                if (!oldpass.equals(newpass)){
                    code = 0;
                    msg = "OK";
                }
                else{
                    code=1;
                    msg="新密码与原密码相同！";
                }
            }
            else {
                code = 2;
                msg="新密码两次输入不一致！";
            }
        }
        else {
            code = 3;
            msg = "原密码错误！";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("code",code);
        map.put("msg",msg);
        return map;
    }

}
