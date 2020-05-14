package com.hqbx.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.mapper.TeacherMapper;
import com.hqbx.model.Teacher;
import com.hqbx.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;
    @Override
    public List<Teacher> getTeacherList() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        List<Teacher> list = (List<Teacher>) redisTemplate.opsForValue().get("TeacherList");
        if (list == null){
            synchronized (this){
                list = (List<Teacher>) redisTemplate.opsForValue().get("TeacherList");
                if (list == null){
                    list = teacherMapper.getTeacherList();
                    redisTemplate.opsForValue().set("TeacherList",list);
                }
            }
        }
        return list;
    }

    @Override
    public Teacher getTeacherById(int id) {
        return teacherMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertTeacher(Teacher teacher) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = teacherMapper.insertSelective(teacher);
        if (i!=0){
            List<Teacher> list = teacherMapper.getTeacherList();
            redisTemplate.opsForValue().set("TeacherList",list);
        }
        return i;
    }

    @Override
    public int delTeacherById(int id) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = teacherMapper.deleteByPrimaryKey(id);
        if (i!=0){
            List<Teacher> list = teacherMapper.getTeacherList();
            redisTemplate.opsForValue().set("TeacherList",list);
        }
        return i;
    }

    @Override
    public int upTeacher(Teacher teacher) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = teacherMapper.updateByPrimaryKeySelective(teacher);
        if (i!=0){
            List<Teacher> list = teacherMapper.getTeacherList();
            redisTemplate.opsForValue().set("TeacherList",list);
        }
        return i;
    }

    @Override
    public int getNum() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        List<Teacher> list = (List<Teacher>) redisTemplate.opsForValue().get("TeacherList");
        return list.size();
    }
}
