package com.hqbx.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.mapper.MaintainerMapper;
import com.hqbx.model.Maintainer;
import com.hqbx.service.MaintainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public class MaintainerServiceImpl implements MaintainerService {
    @Autowired
    MaintainerMapper maintainerMapper;
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;
    @Override
    public List<Maintainer> getMaintainerList() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        List<Maintainer> list = (List<Maintainer>) redisTemplate.opsForValue().get("MaintainerList");
        if (list == null){
            synchronized (this){
                list = (List<Maintainer>) redisTemplate.opsForValue().get("MaintainerList");
                if (list == null){
                    list = maintainerMapper.getMaintainerList();
                    redisTemplate.opsForValue().set("MaintainerList",list);
                }
            }
        }
        return list;
    }

    @Override
    public Maintainer getMaintainerById(int id) {
        return maintainerMapper.selectByPrimaryKey(id);
    }

    @Override
    public Maintainer getMaintainerByusername(String username) {
        return maintainerMapper.getMaintainerByusername(username);
    }

    @Override
    public int insertMaintainer(Maintainer maintainer) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = maintainerMapper.insertSelective(maintainer);
        if (i!=0){
            List<Maintainer> list = maintainerMapper.getMaintainerList();
            redisTemplate.opsForValue().set("MaintainerList",list);
        }
        return i;
    }

    @Override
    public int delMaintainerByid(int id) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = maintainerMapper.deleteByPrimaryKey(id);
        if (i!=0){
            List<Maintainer> list = maintainerMapper.getMaintainerList();
            redisTemplate.opsForValue().set("MaintainerList",list);
        }
        return i;
    }

    @Override
    public int upMaintainer(Maintainer maintainer) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = maintainerMapper.updateByPrimaryKeySelective(maintainer);
        if (i!=0){
            List<Maintainer> list = maintainerMapper.getMaintainerList();
            redisTemplate.opsForValue().set("MaintainerList",list);
        }
        return i;
    }

    @Override
    public int getNum() {
        return maintainerMapper.getNum();
    }
}
