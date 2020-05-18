package com.hqbx.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hqbx.model.Log;
import com.hqbx.model.UserInfo;
import com.hqbx.service.LogService;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;

@RestController
public class setLog {
    @Reference
    static private LogService logService;
    static public boolean setlod(HttpServletRequest httpServletRequest,String caozuo){
        HttpSession httpSession = httpServletRequest.getSession();
        if (httpSession.getAttribute("islogin")!=null)
        {
            UserInfo users = (UserInfo) httpSession.getAttribute("islogin");
            int username = users.getId();
            Date date = new Date(System.currentTimeMillis()+(8*60*60*1000));
            Log log = new Log();
            log.setCz(caozuo);
            log.setCzr(username);
            log.setTime(date);
            logService.insertSelective(log);
            return true;
        }
        return false;
    }

    static public boolean setlod(Integer czr,String caozuo){
            Date date = new Date(System.currentTimeMillis()+(8*60*60*1000));
            Log log = new Log();
            log.setCz(caozuo);
            log.setCzr(czr);
            log.setTime(date);
            if (logService.insertSelective(log)!=0)
                return true;
            return false;
    }
}
