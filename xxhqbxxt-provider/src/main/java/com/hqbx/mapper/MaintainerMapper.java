package com.hqbx.mapper;

import com.hqbx.model.Maintainer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Mapper
public interface MaintainerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Maintainer record);

    int insertSelective(Maintainer record);

    Maintainer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Maintainer record);

    int updateByPrimaryKey(Maintainer record);

    List<Maintainer> getMaintainerList();

    Maintainer getMaintainerByusername(String username);

    int getNum();
}