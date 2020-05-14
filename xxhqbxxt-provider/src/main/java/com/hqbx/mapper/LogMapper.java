package com.hqbx.mapper;

import com.hqbx.model.Log;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface LogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Integer id);

    List<Log> getlogList();

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);
}