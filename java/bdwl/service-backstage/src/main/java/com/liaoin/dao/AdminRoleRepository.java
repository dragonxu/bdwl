package com.liaoin.dao;

import com.liaoin.bean.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminRoleRepository extends JpaRepository<AdminRole,String> {

    void deleteByAdminId(String adminId);

    void deleteByRoleId(String roleId);

    AdminRole findByAdminIdAndRoleId(String adminId,String roleId);

    List<AdminRole> findByAdminId(String adminId);

    @Query(value = "SELECT role_id from t_admin_role WHERE admin_id =?1",nativeQuery = true)
    List<String> queryRoleIdsByAdminId(String adminId);
}
