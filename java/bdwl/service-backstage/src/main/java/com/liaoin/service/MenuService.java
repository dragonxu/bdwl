package com.liaoin.service;

import com.liaoin.Enum.Status;
import com.liaoin.bean.Menu;
import com.liaoin.message.OperateResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MenuService {

    /**
     * 获取菜单列表
     */
    public List<Menu> queryLists(String parentId, Status status,List<String> roleIds);

    public List<Menu> queryMenus(String menuId);

    public List<Menu> queryAdminMenus(List<String> menuIds,String menuId);

    /**
     * 保存编辑菜单信息
     */
    public OperateResult save(Menu menu);

    public Menu findById(String id);

    /**
     * 递归获取
     */
    public String queryMenus(String menuId,int index,String parentMenuId);

    public OperateResult delete(String id);

    /**
     * 递归获取
     */
    public String queryMenus(String menuId, HttpServletRequest request);

    /**
     * 递归获取
     */
    public String queryMenus(String menuId,String roleId,int index);
}
