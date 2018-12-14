package com.liaoin.dao;

import com.liaoin.bean.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminRepository extends JpaRepository<Admin,String>,JpaSpecificationExecutor<Admin> {

    /**
     * 根据用户名查询用户
     */
    Admin findByUsername(String username);

    Admin findByUsernameAndIdNot(String username,String id);
}
