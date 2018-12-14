package com.liaoin.service;

import com.liaoin.bean.Admin;
import com.liaoin.bean.AdminRole;
import com.liaoin.message.OperateResult;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AdminService {

    /**
     * 登录
     */
    public OperateResult login(HttpServletRequest request, String phone, String password);

    public Page<Admin> queryWithPage(int page, int size);

    public OperateResult save(Admin admin,String[] roleIds);

    public Admin findById(String id);

    public OperateResult updatePassword(Admin admin,String oldPassword,String password,String repassword);

    public List<String> queryAdminRoleIdsList(Admin admin);
}
