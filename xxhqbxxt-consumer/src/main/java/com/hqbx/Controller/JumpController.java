package com.hqbx.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hqbx.model.*;
import com.hqbx.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class JumpController {
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

    @RequestMapping("/")
    public String Shouye(Model model,HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("islogin")!=null) {
            UserInfo userInfo = (UserInfo) session.getAttribute("islogin");
            int id = userInfo.getId();
            String pass = userInfo.getPassword();
            int qxz = userInfo.getQx();
            if (qxz==1){
                if (adminService.getAdminById(id).getPass().equals(pass)) {
                    model.addAttribute("users", userInfo);
                    int gid = userInfo.getQx();
                    Groupinfo groupinfo = groupinfoService.selectByPrimaryKey(gid);
                    if (groupinfo == null) {
                        model.addAttribute("error", "对不起您访问的用户组不存在或已被删除");
                        session.invalidate();
                        return "errpage";
                    }
                    String quanxian = groupinfo.getQx();
                    String qx[] = {};
                    qx = quanxian.split(",");
                    Map map = new HashMap();
                    for (String qxcache : qx) {
                        int qxid = Integer.parseInt(qxcache);
                        Map map1 = new HashMap();
                        map1.put("pmenu", menuService.selectByPrimaryKey(qxid));
                        map1.put("cmenu", menuService.getmenubyfdm(qxid));
                        map.put(String.valueOf(qxid) + "menus", map1);
                    }
                    model.addAttribute("parentmenus", map);
                    setLog.setlod(httpServletRequest, "id为" + id + "的用户登录");
                    return "views/index";
                }
                else {
                    model.addAttribute("error", "密码不正确！可能您已修改密码");
                    session.invalidate();
                    return "errpage";
                }
            }
            if (qxz==2){
                if (maintainerService.getMaintainerById(id).getPass().equals(pass)) {
                    model.addAttribute("users", userInfo);
                    int gid = userInfo.getQx();
                    Groupinfo groupinfo = groupinfoService.selectByPrimaryKey(gid);
                    if (groupinfo == null) {
                        model.addAttribute("error", "对不起您访问的用户组不存在或已被删除");
                        session.invalidate();
                        return "errpage";
                    }
                    String quanxian = groupinfo.getQx();
                    String qx[] = {};
                    qx = quanxian.split(",");
                    Map map = new HashMap();
                    for (String qxcache : qx) {
                        int qxid = Integer.parseInt(qxcache);
                        Map map1 = new HashMap();
                        map1.put("pmenu", menuService.selectByPrimaryKey(qxid));
                        map1.put("cmenu", menuService.getmenubyfdm(qxid));
                        map.put(String.valueOf(qxid) + "menus", map1);
                    }
                    model.addAttribute("parentmenus", map);
                    setLog.setlod(httpServletRequest, "id为" + id + "的用户登录");
                    return "views/index";
                }
                else {
                    model.addAttribute("error", "密码不正确！可能您已修改密码");
                    session.invalidate();
                    return "errpage";
                }
            }
            model.addAttribute("error", "系统错误！");
            session.invalidate();
            return "errpage";
        }
        else
            return "login";
    }
    @RequestMapping("/Jumpto")
    public String Jumpto(@RequestParam("url") String url){
        return url;
    }
    @RequestMapping("/resetpassword")
    public String resetpassword()
    {return  "views/table/xgmm";}
    @RequestMapping("/login")
    public String login(HttpServletRequest httpServletRequest, @RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        HttpSession session = httpServletRequest.getSession();
        int qxz=2;
        Admin admin = adminService.getAdminByUname(username);
        if (admin!=null){
            if (admin.getPass().equals(password)){
                qxz=1;
                int id = admin.getId();
                model.addAttribute("users",admin);
                int gid = qxz;
                Groupinfo groupinfo = groupinfoService.selectByPrimaryKey(gid);
                if (groupinfo==null)
                {
                    model.addAttribute("error","对不起您访问的用户组不存在或已被删除");
                    session.invalidate();
                    return "login";
                }
                String quanxian = groupinfo.getQx();
                String qx[] = {};
                qx = quanxian.split(",");
                Map map = new HashMap();
                for (String qxcache:qx){
                    int qxid = Integer.parseInt(qxcache);
                    Map map1 = new HashMap();
                    map1.put("pmenu",menuService.selectByPrimaryKey(qxid));
                    map1.put("cmenu",menuService.getmenubyfdm(qxid));
                    map.put(String.valueOf(qxid)+"menus",map1);
                }
                model.addAttribute("parentmenus",map);
                UserInfo userInfo = new UserInfo();
                userInfo.setId(admin.getId());
                userInfo.setQx(1);
                userInfo.setUsername(admin.getUname());
                userInfo.setPassword(admin.getPass());
                session.setAttribute("islogin",userInfo);
                setLog.setlod(httpServletRequest, "id为"+id+"的用户登录");
                return "views/index";
            }
            else
            {
                model.addAttribute("error","密码错误");
                return "login";
            }
        }
        else if (username.equals("admin")){
            if (password.equals("admin")){
                qxz=1;
                Admin admin1 = new Admin();
                admin1.setId(0);
                admin1.setUname("admin");
                model.addAttribute("users",admin1);
                int gid = qxz;
                Groupinfo groupinfo = groupinfoService.selectByPrimaryKey(gid);
                if (groupinfo==null)
                {
                    model.addAttribute("error","对不起您访问的用户组不存在或已被删除");
                    session.invalidate();
                    return "login";
                }
                String quanxian = groupinfo.getQx();
                String qx[] = {};
                qx = quanxian.split(",");
                Map map = new HashMap();
                for (String qxcache:qx){
                    int qxid = Integer.parseInt(qxcache);
                    Map map1 = new HashMap();
                    map1.put("pmenu",menuService.selectByPrimaryKey(qxid));
                    map1.put("cmenu",menuService.getmenubyfdm(qxid));
                    map.put(String.valueOf(qxid)+"menus",map1);
                }
                model.addAttribute("parentmenus",map);
                setLog.setlod(httpServletRequest, "id为"+0+"的用户登录");
                return "views/index";
            }
            else {
                model.addAttribute("error","密码错误");
                return "login";
            }
        }
        else {
            Maintainer maintainer = maintainerService.getMaintainerByusername(username);
            if (maintainer!=null){
                if (maintainer.getPass().equals(password)){
                    qxz = 2;
                    int id = maintainer.getId();
                    model.addAttribute("users",maintainer);
                    int gid = qxz;
                    Groupinfo groupinfo = groupinfoService.selectByPrimaryKey(gid);
                    if (groupinfo==null)
                    {
                        model.addAttribute("error","对不起您访问的用户组不存在或已被删除");
                        session.invalidate();
                        return "login";
                    }
                    String quanxian = groupinfo.getQx();
                    String qx[] = {};
                    qx = quanxian.split(",");
                    Map map = new HashMap();
                    for (String qxcache:qx){
                        int qxid = Integer.parseInt(qxcache);
                        Map map1 = new HashMap();
                        map1.put("pmenu",menuService.selectByPrimaryKey(qxid));
                        map1.put("cmenu",menuService.getmenubyfdm(qxid));
                        map.put(String.valueOf(qxid)+"menus",map1);
                    }
                    model.addAttribute("parentmenus",map);
                    UserInfo userInfo = new UserInfo();
                    userInfo.setId(maintainer.getId());
                    userInfo.setQx(2);
                    userInfo.setUsername(maintainer.getUname());
                    userInfo.setPassword(maintainer.getPass());
                    session.setAttribute("islogin",userInfo);
                    setLog.setlod(httpServletRequest, "id为"+id+"的用户登录");
                    return "views/index";
                }
                else
                {
                    model.addAttribute("error","密码错误");
                    return "login";
                }
            }
            else {
                model.addAttribute("error","账户不存在");
                return "login";
            }
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest){
        HttpSession httpSession = httpServletRequest.getSession();
        setLog.setlod(httpServletRequest, "安全退出");
        httpSession.invalidate();
        return "logout";
    }
}
