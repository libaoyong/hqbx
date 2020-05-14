package com.hqbx.mapper;

import com.hqbx.model.Groupinfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface GroupinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Groupinfo record);

    int insertSelective(Groupinfo record);

    Groupinfo selectByPrimaryKey(Integer id);

    List<Groupinfo> getgroupinfoList();

    int updateByPrimaryKeySelective(Groupinfo record);

    int updateByPrimaryKey(Groupinfo record);
}