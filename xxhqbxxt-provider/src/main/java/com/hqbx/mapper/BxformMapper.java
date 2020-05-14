package com.hqbx.mapper;

import com.hqbx.model.Bxform;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Component
@Mapper
public interface BxformMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bxform record);

    int insertSelective(Bxform record);

    Bxform selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bxform record);

    int updateByPrimaryKey(Bxform record);

    List<Bxform> getBxformList();

    List<Date> getTimeGroup();

    List<Bxform> getBxformByuid(int id);

    List<Bxform> getBxformBymid(int id);

    int getNumByDate(Date date);

    int getNum();

    int initBxform(Bxform record);
}