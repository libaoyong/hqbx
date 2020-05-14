package com.hqbx.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.mapper.GroupinfoMapper;
import com.hqbx.model.Groupinfo;
import com.hqbx.service.GroupinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public class GroupinfoServiceImpl implements GroupinfoService {
    @Autowired
    private GroupinfoMapper groupinfoMapper;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = groupinfoMapper.deleteByPrimaryKey(id);
        if (i!=0){
            List<Groupinfo> list = groupinfoMapper.getgroupinfoList();
            redisTemplate.opsForValue().set("groupinfoList",list);
        }
        return i;
    }

    @Override
    public int insertSelective(Groupinfo record) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = groupinfoMapper.insertSelective(record);
        if (i !=0 ){
            List<Groupinfo> list = groupinfoMapper.getgroupinfoList();
            redisTemplate.opsForValue().set("groupinfoList",list);
        }
        return i;
    }

    @Override
    public Groupinfo selectByPrimaryKey(Integer id) {
        return groupinfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Groupinfo> getgroupinfoList() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        List<Groupinfo> list = (List<Groupinfo>) redisTemplate.opsForValue().get("groupinfoList");
        if (list==null){
            synchronized (this){
                list = (List<Groupinfo>) redisTemplate.opsForValue().get("groupinfoList");
                if (list == null)
                {
                    list = groupinfoMapper.getgroupinfoList();
                    redisTemplate.opsForValue().set("groupinfoList",list);
                }
            }
        }
        return list;
    }

    @Override
    public int updateByPrimaryKeySelective(Groupinfo record) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = groupinfoMapper.updateByPrimaryKeySelective(record);
        if (i!=0){
            List<Groupinfo> list = groupinfoMapper.getgroupinfoList();
            redisTemplate.opsForValue().set("groupinfoList",list);
        }
        return i;
    }
}
