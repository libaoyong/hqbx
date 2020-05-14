package com.hqbx.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.mapper.StudentMapper;
import com.hqbx.model.Student;
import com.hqbx.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;
    @Override
    public List<Student> getStudentList() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        List<Student> list = (List<Student>) redisTemplate.opsForValue().get("StudentList");
        if (list == null){
            synchronized (this){
                list = (List<Student>) redisTemplate.opsForValue().get("StudentList");
                if (list == null){
                    list = studentMapper.getStudentList();
                    redisTemplate.opsForValue().set("StudentList",list);
                }
            }
        }
        return list;
    }

    @Override
    public Student getStudentByid(int id) {
        return studentMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertStudent(Student student) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = studentMapper.insertSelective(student);
        if (i!=0){
            List<Student> list = studentMapper.getStudentList();
            redisTemplate.opsForValue().set("StudentList",list);
        }
        return i;
    }

    @Override
    public int delStudentById(int id) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = studentMapper.deleteByPrimaryKey(id);
        if (i!=0){
            List<Student> list = studentMapper.getStudentList();
            redisTemplate.opsForValue().set("StudentList",list);
        }
        return i;
    }

    @Override
    public int upStudent(Student student) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int i = 0;
        i = studentMapper.updateByPrimaryKeySelective(student);
        if (i!=0){
            List<Student> list = studentMapper.getStudentList();
            redisTemplate.opsForValue().set("StudentList",list);
        }
        return i;
    }

    @Override
    public int getNum() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        List<Student> list = (List<Student>) redisTemplate.opsForValue().get("StudentList");
        return list.size();
    }
}
