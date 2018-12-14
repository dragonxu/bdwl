package com.liaoin.service;

import com.liaoin.Enum.RoleStatus;
import com.liaoin.bean.AdminRole;
import com.liaoin.bean.Role;
import com.liaoin.message.OperateResult;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {

    /**
     * 获取全部角色
     */
    public List<Role> findAllRoleLists();

    public List<Role> findAllRoleLists(String adminId);

    /**
     * 保存角色信息
     */
    public OperateResult save(String id, String name, RoleStatus roleStatus, String introduce);

    /**
     * 通过id查询角色信息
     */
    public Role findById(String id);

    /**
     * 删除角色信息
     */
    public OperateResult deleteById(String id);

    /**
     * 设置角色权限
     */
    public OperateResult setRolePowers(String roleId,String[] powerIds);

    public Page<Role> queryWithPage(int page, int size);

    public List<AdminRole> findByAdminId(String adminId);

    public List<String> queryRoleList(List<String> roleIds);

}
