package com.hqbx.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.model.Teacher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public interface TeacherService {
    List<Teacher> getTeacherList();

    Teacher getTeacherById(int id);

    int insertTeacher(Teacher teacher);

    int delTeacherById(int id);

    int upTeacher(Teacher teacher);

    int getNum();
}
