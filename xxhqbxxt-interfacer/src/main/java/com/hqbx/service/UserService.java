package com.hqbx.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public interface UserService {
    List<User> getUserList();

    User getUserById(int id);

    User getUserByOpenid(String openid);

    int insertUser(User user);

    int delUser(int id);

    int upUser(User user);

    int getNum();

    User getUserByUid(int uid);
}
