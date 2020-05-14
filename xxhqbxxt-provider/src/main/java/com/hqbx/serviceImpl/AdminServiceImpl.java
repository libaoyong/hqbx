package com.hqbx.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqbx.mapper.AdminMapper;
import com.hqbx.model.Admin;
import com.hqbx.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    RedisTemplate<Object,Object> template;
    @Override
    public List<Admin> getAdminList() {
        template.setKeySerializer(new StringRedisSerializer());
        List<Admin> admins = (List<Admin>) template.opsForValue().get("AdminList");
        if (admins==null){
            synchronized (this){
                admins = (List<Admin>) template.opsForValue().get("AdminList");
                if (admins==null){
                    admins = adminMapper.getAdminList();
                    template.opsForValue().set("AdminList",admins);
                }
            }
        }
        return admins;
    }

    @Override
    public Admin getAdminById(int id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public Admin getAdminByUname(String username) {
        return adminMapper.getAdminByUname(username);
    }

    @Override
    public int insertAdmin(Admin admin) {
        int i = 0;
        template.setKeySerializer(new StringRedisSerializer());
        i = adminMapper.insertSelective(admin);
        if (i!=0){
            List<Admin> admins = adminMapper.getAdminList();
            template.opsForValue().set("AdminList",admins);
        }
        return i;
    }

    @Override
    public int delAdminById(int id) {
        int i = 0;
        template.setKeySerializer(new StringRedisSerializer());
        i = adminMapper.deleteByPrimaryKey(id);
        if (i!=0){
            List<Admin> admins = adminMapper.getAdminList();
            template.opsForValue().set("AdminList",admins);
        }
        return i;
    }

    @Override
    public int upAdmin(Admin admin) {
        int i = 0;
        template.setKeySerializer(new StringRedisSerializer());
        i = adminMapper.updateByPrimaryKeySelective(admin);
        if (i!=0){
            List<Admin> admins = adminMapper.getAdminList();
            template.opsForValue().set("AdminList",admins);
        }
        return i;
    }
}
