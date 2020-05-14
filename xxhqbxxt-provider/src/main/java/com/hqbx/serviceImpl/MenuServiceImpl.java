package com.hqbx.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.mapper.MenuMapper;
import com.hqbx.model.Menu;
import com.hqbx.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = menuMapper.deleteByPrimaryKey(id);
        if (i!=0){
            List<Menu> list = menuMapper.getmenuList();
            redisTemplate.opsForValue().set("menuList",list);
        }
        return i;
    }

    @Override
    public int insertSelective(Menu record) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = menuMapper.insertSelective(record);
        if (i!=0){
            List<Menu> list = menuMapper.getmenuList();
            redisTemplate.opsForValue().set("menuList",list);
        }
        return i;
    }

    @Override
    public List<Menu> getmenuList() {
       redisTemplate.setKeySerializer(new StringRedisSerializer());
       List<Menu> list = (List<Menu>) redisTemplate.opsForValue().get("menuList");
       if (list==null){
           synchronized (this){
               list = (List<Menu>) redisTemplate.opsForValue().get("menuList");
               if (list == null){
                   list = menuMapper.getmenuList();
                   redisTemplate.opsForValue().set("menuList",list);
               }
           }
       }
       return  list;
    }

    @Override
    public List<Menu> getfdmmenuList() {
        return menuMapper.getfdmmenuList();
    }

    @Override
    public List<Menu> getmenubyfdm(int fdm) {
        return menuMapper.getmenubyfdm(fdm);
    }

    @Override
    public Menu selectByPrimaryKey(Integer id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Menu record) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = menuMapper.updateByPrimaryKeySelective(record);
        if (i!=0){
            List<Menu> list = menuMapper.getmenuList();
            redisTemplate.opsForValue().set("menuList",list);
        }
        return i;
    }
}
