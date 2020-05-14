package com.hqbx.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.model.Student;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public interface StudentService {
    List<Student> getStudentList();

    Student getStudentByid(int id);

    int insertStudent(Student student);

    int delStudentById(int id);

    int upStudent(Student student);

    int getNum();
}
