package com.liaoin.dao;

import com.liaoin.bean.RolePower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RolePowerRepository extends JpaRepository<RolePower,String> {

    void deleteByRoleId(String roleId);

//    void deleteByPowerId(String powerId);

//    List<RolePower> findByPowerIdAndRoleIdIn(String powerId, List<String> roleIds);

//    @Query(value = "SELECT power_id from t_role_power WHERE role_id in ?1",nativeQuery = true)
//    List<String> findPowerIdsByRoleId(List<String> roleIds);

    List<RolePower> findByMenuIdAndRoleId(String menuId, String roleId);

    @Query(value = "SELECT menu_id from t_role_power WHERE role_id in ?1",nativeQuery = true)
    List<String> findPowerIdsByRoleId(List<String> roleIds);
}
