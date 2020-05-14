package com.hqbx.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.mapper.BxformMapper;
import com.hqbx.model.Bxform;
import com.hqbx.service.BxformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Service
public class BxformServiceImpl implements BxformService {
    @Autowired
    BxformMapper bxformMapper;
    @Autowired
    RedisTemplate<Object,Object> template;
    @Override
    public List<Bxform> getBxformList() {
        template.setKeySerializer(new StringRedisSerializer());
        List<Bxform> list = (List<Bxform>) template.opsForValue().get("BxformList");
        if (list==null){
            synchronized (this){
                list = (List<Bxform>) template.opsForValue().get("BxformList");
                if (list == null){
                    list = bxformMapper.getBxformList();
                    template.opsForValue().set("BxformList",list);
                }
            }
        }
        return list;
    }

    @Override
    public Bxform getBxformById(int id) {
        return bxformMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Date> getTimeGroup() {
        return bxformMapper.getTimeGroup();
    }

    @Override
    public List<Bxform> getBxformByuid(int id) {
        return bxformMapper.getBxformByuid(id);
    }

    @Override
    public List<Bxform> getBxformBymid(int id) {
        return bxformMapper.getBxformBymid(id);
    }

    @Override
    public int getNumByDate(Date date) {
        return bxformMapper.getNumByDate(date);
    }

    @Override
    public int insertBxform(Bxform bxform) {
        int i = 0;
        i = bxformMapper.insertSelective(bxform);
        if (i!=0){
            List<Bxform> list = bxformMapper.getBxformList();
            template.opsForValue().set("BxformList",list);
        }
        return i;
    }

    @Override
    public int delBxformById(int id) {
        int i = 0;
        i = bxformMapper.deleteByPrimaryKey(id);
        if (i!=0){
            List<Bxform> list = bxformMapper.getBxformList();
            template.opsForValue().set("BxformList",list);
        }
        return i;
    }

    @Override
    public int upBxform(Bxform bxform) {
        int i = 0;
        i = bxformMapper.updateByPrimaryKeySelective(bxform);
        if (i!=0){
            List<Bxform> list = bxformMapper.getBxformList();
            template.opsForValue().set("BxformList",list);
        }
        return i;
    }

    @Override
    public int getNum() {
        return bxformMapper.getNum();
    }

    @Override
    public int initBxform(Bxform record) {
        int i = 0;
        i = bxformMapper.initBxform(record);
        if (i!=0){
            List<Bxform> list = bxformMapper.getBxformList();
            template.opsForValue().set("BxformList",list);
        }
        return i;
    }
}
