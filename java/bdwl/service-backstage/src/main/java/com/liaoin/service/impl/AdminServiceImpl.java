package com.liaoin.service.impl;

import com.liaoin.Enum.Result;
import com.liaoin.bean.Admin;
import com.liaoin.bean.AdminRole;
import com.liaoin.bean.Role;
import com.liaoin.dao.AdminRepository;
import com.liaoin.dao.AdminRoleRepository;
import com.liaoin.message.OperateResult;
import com.liaoin.service.AdminService;
import com.liaoin.util.MD5Encoder;
import com.liaoin.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import java.util.List;

import static com.liaoin.message.OperateResult.fail;
import static com.liaoin.message.OperateResult.success;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminRoleRepository adminRoleRepository;

    @Override
    public OperateResult login(HttpServletRequest request, String username, String password) {
        Admin admin = adminRepository.findByUsername(username);
        if(admin == null){
            return fail(Result.NO_USER);
        }
        String confirmPassword = MD5Encoder.encryption(admin.getRandom() + MD5Encoder.encryption(password));
        if(confirmPassword.equals(admin.getPassword())){
            int random = (int)(Math.random()*9000 + 1000);
            admin.setRandom(random);
            confirmPassword = MD5Encoder.encryption(random + MD5Encoder.encryption(password));
            admin.setPassword(confirmPassword);
            adminRepository.save(admin);
            request.getSession().setAttribute("admin",admin);
            return success();
        }else{
            return fail(Result.ERROR_PASSWORD);
        }
    }

    @Override
    public Page<Admin> queryWithPage(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(page,size,sort);
        Page<Admin> admins = adminRepository.findAll(pageRequest);
        return admins;
    }

    @Override
    public OperateResult save(Admin paramAdmin,String[] roleIds) {
        Admin admin = null;
        if(StringUtils.isNotNull(paramAdmin.getId())){
            admin = adminRepository.findById(paramAdmin.getId()).get();
        }else{
            admin = new Admin();
        }

        admin.setUsername(paramAdmin.getUsername());
        if(StringUtils.isNotNull(paramAdmin.getPassword())){
            int random = (int)(Math.random()*9000 + 1000);
            admin.setRandom(random);
            String confirmPassword = MD5Encoder.encryption(random + MD5Encoder.encryption(paramAdmin.getPassword()));
            admin.setPassword(confirmPassword);
        }

        if(StringUtils.isNotNull(paramAdmin.getId())){
            if(adminRepository.findByUsernameAndIdNot(paramAdmin.getUsername(),paramAdmin.getId()) != null){
                return fail(Result.HAVE_USER);
            }
        }else{
            if(adminRepository.findByUsername(paramAdmin.getUsername()) != null ){
                return fail(Result.HAVE_USER);
            }
        }
        adminRepository.save(admin);

        //用户权限设置
        if(StringUtils.isNotNull(paramAdmin.getId())){
            adminRoleRepository.deleteByAdminId(admin.getId());
        }
        for (String roleId : roleIds){
            AdminRole adminRole = new AdminRole();
            adminRole.setRole(Role.builder().id(roleId).build());
            adminRole.setAdmin(Admin.builder().id(admin.getId()).build());
            adminRoleRepository.save(adminRole);
        }
        return success();
    }

    public Admin findById(String id){
        if(adminRepository.existsById(id)){
            return adminRepository.findById(id).get();
        }else{
            return null;
        }
    }

    @Override
    public OperateResult updatePassword(Admin admin, String oldPassword, String password, String repassword) {

        if(!password.equals(repassword)){
            return  fail(Result.TWICE_PASSWORD_NOT_EQUAL);
        }else{
            String confirmPassword = MD5Encoder.encryption(admin.getRandom() + MD5Encoder.encryption(oldPassword));
            if(!confirmPassword.equalsIgnoreCase(admin.getPassword())){
              return fail(Result.OLD_PASSWORD_NOT_EQUAL);
            }else{
                int random = (int)(Math.random()*9000 + 1000);
                admin.setRandom(random);
                confirmPassword = MD5Encoder.encryption(random + MD5Encoder.encryption(password));
                admin.setPassword(confirmPassword);
                adminRepository.save(admin);
                return success();
            }
        }
    }

    @Override
    public List<String> queryAdminRoleIdsList(Admin admin) {
        return adminRoleRepository.queryRoleIdsByAdminId(admin.getId());
    }
}
