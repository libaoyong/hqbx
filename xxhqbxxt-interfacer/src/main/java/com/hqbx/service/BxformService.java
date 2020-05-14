package com.hqbx.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.model.Bxform;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Service
public interface BxformService {
    List<Bxform> getBxformList();

    Bxform getBxformById(int id);

    List<Date> getTimeGroup();

    List<Bxform> getBxformByuid(int id);

    List<Bxform> getBxformBymid(int id);

    int getNumByDate(Date date);

    int insertBxform(Bxform bxform);

    int delBxformById(int id);

    int upBxform(Bxform bxform);

    int getNum();

    int initBxform(Bxform record);
}
