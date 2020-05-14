package com.hqbx.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.model.Maintainer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public interface MaintainerService {
    List<Maintainer> getMaintainerList();

    Maintainer getMaintainerById(int id);

    Maintainer getMaintainerByusername(String username);

    int insertMaintainer(Maintainer maintainer);

    int delMaintainerByid(int id);

    int upMaintainer(Maintainer maintainer);

    int getNum();
}
