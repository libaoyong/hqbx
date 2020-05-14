package com.hqbx.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.model.Groupinfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public interface GroupinfoService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Groupinfo record);

    Groupinfo selectByPrimaryKey(Integer id);

    List<Groupinfo> getgroupinfoList();

    int updateByPrimaryKeySelective(Groupinfo record);
}
