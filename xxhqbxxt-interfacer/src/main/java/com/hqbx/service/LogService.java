package com.hqbx.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.model.Log;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public interface LogService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Integer id);

    List<Log> getlogList();

    int updateByPrimaryKeySelective(Log record);
}
