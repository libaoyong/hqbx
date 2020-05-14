package com.hqbx.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.mapper.UserMapper;
import com.hqbx.model.User;
import com.hqbx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;
    @Override
    public List<User> getUserList() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        List<User> list = (List<User>) redisTemplate.opsForValue().get("UserList");
        if (list == null){
            synchronized (this){
                list = (List<User>) redisTemplate.opsForValue().get("UserList");
                if (list == null){
                    list = userMapper.getUserList();
                    redisTemplate.opsForValue().set("UserList",list);
                }
            }
        }
        return list;
    }

    @Override
    public User getUserById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getUserByOpenid(String openid) {
        return userMapper.getUserByOpenid(openid);
    }

    @Override
    public int insertUser(User user) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = userMapper.insertSelective(user);
        if (i!=0){
            List<User> list = userMapper.getUserList();
            redisTemplate.opsForValue().set("UserList",list);
        }
        return i;
    }

    @Override
    public int delUser(int id) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = userMapper.deleteByPrimaryKey(id);
        if (i!=0){
            List<User> list = userMapper.getUserList();
            redisTemplate.opsForValue().set("UserList",list);
        }
        return i;
    }

    @Override
    public int upUser(User user) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = userMapper.updateByPrimaryKeySelective(user);
        if (i!=0){
            List<User> list = userMapper.getUserList();
            redisTemplate.opsForValue().set("UserList",list);
        }
        return i;
    }

    @Override
    public int getNum() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        List<User> list = (List<User>) redisTemplate.opsForValue().get("UserList");
        return list.size();
    }

    @Override
    public User getUserByUid(int uid) {
        return userMapper.getUserByUid(uid);
    }
}
