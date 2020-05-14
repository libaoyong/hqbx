package com.hqbx.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.model.Menu;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public interface MenuService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Menu record);

    List<Menu> getmenuList();

    List<Menu> getfdmmenuList();

    List<Menu> getmenubyfdm(int fdm);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);
}
