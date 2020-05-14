package com.hqbx.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.mapper.LogMapper;
import com.hqbx.model.Log;
import com.hqbx.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = logMapper.deleteByPrimaryKey(id);
        if (i!=0){
            List<Log> list = logMapper.getlogList();
            redisTemplate.opsForValue().set("logList",list);
        }
        return i;
    }

    @Override
    public int insertSelective(Log record) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = logMapper.insertSelective(record);
        if (i!=0){
            List<Log> list = logMapper.getlogList();
            redisTemplate.opsForValue().set("logList",list);
        }
        return i;
    }

    @Override
    public Log selectByPrimaryKey(Integer id) {
        return logMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Log> getlogList() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        List<Log> list = (List<Log>) redisTemplate.opsForValue().get("logList");
        if (list==null){
            synchronized (this){
                list = (List<Log>) redisTemplate.opsForValue().get("logList");
                if (list==null){
                    list = logMapper.getlogList();
                    redisTemplate.opsForValue().set("logList",list);
                }
            }
        }
        return list;
    }

    @Override
    public int updateByPrimaryKeySelective(Log record) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = logMapper.updateByPrimaryKeySelective(record);
        if (i!=0){
            List<Log> list = logMapper.getlogList();
            redisTemplate.opsForValue().set("logList",list);
        }
        return i;
    }
}
