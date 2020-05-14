package com.hqbx.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.model.Admin;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public interface AdminService {
    List<Admin> getAdminList();

    Admin getAdminById(int id);

    Admin getAdminByUname(String username);

    int insertAdmin(Admin admin);

    int delAdminById(int id);

    int upAdmin(Admin admin);
}
