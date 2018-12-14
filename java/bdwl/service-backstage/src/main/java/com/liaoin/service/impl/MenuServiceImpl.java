package com.liaoin.service.impl;

import com.liaoin.Enum.Status;
import com.liaoin.bean.Menu;
import com.liaoin.bean.RolePower;
import com.liaoin.dao.MenuRepository;
import com.liaoin.dao.RolePowerRepository;
import com.liaoin.message.OperateResult;
import com.liaoin.service.MenuService;
import com.liaoin.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RolePowerRepository rolePowerRepository;

    /**
     * 获取菜单猎豹
     */
    public List<Menu> queryLists(String parentId, Status status,List<String> roleIds){
        Specification<Menu> specification = new Specification<Menu>() {
            @Override
            public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if(StringUtils.isNotNull(parentId)) {
                    predicate.add(cb.equal(root.get("parentId").as(String.class), parentId));
                }else{
                    predicate.add(cb.equal(root.get("parentId").as(String.class),"0"));
                }

                if(status != null){
                    predicate.add(cb.equal(root.get("status").as(Status.class), status));
                }

                if(roleIds != null && roleIds.size() > 0){
                    CriteriaBuilder.In<String> roleIdsLists = cb.in(root.get("id"));
                    for (String roleId : roleIds){
                        roleIdsLists.value(roleId);
                    }
                    predicate.add(roleIdsLists);
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };

        List<Menu> menus = menuRepository.findAll(specification);
        return menus;
    }

    public List<Menu> queryMenus(String menuId){
        List<Menu> menus = queryLists(menuId,Status.BLOCK,null);
        if(menus.size() > 0){
            for (Menu menu : menus){
                menu.setMenus(queryMenus(menu.getId()));
            }
        }
        return menus;
    }

    /**
     * 保存编辑菜单信息
     */
    public OperateResult save(Menu menu){
        if(menu.getId() != null && !menu.getId().equals("")){
            if(menuRepository.existsById(menu.getId())){
                menu.setId(menu.getId());
            }
        }
        menuRepository.save(menu);
        return OperateResult.success();
    }

    public Menu findById(String id){
        if(menuRepository.existsById(id)){
            return menuRepository.findById(id).get();
        }else{
            return null;
        }
    }

    /**
     * 递归获取
     */
    public String queryMenus(String menuId,int index,String parentMenuId){
        StringBuilder textbookString = new StringBuilder("");
        List<Menu> menus = queryLists(menuId,null,null);
        if(menus.size() > 0){
            String empty = "";
            for (int i = 0 ; i < index ; i++){
                empty = empty + "&nbsp;";
            }
            for(Menu menu : menus){
                String selected = "";
                if(StringUtils.isNotNull(parentMenuId) && parentMenuId.equals(menu.getId())){
                    selected = "selected";
                }
                String textbookStr = "<option value='" + menu.getId() + "'"+ selected +">" + empty + menu.getName() + "</option>" + queryMenus(menu.getId(), index + 4, parentMenuId);
                textbookString.append(textbookStr);
            }
        }
        return textbookString.toString();
    }

    public OperateResult delete(String id){
        menuRepository.deleteById(id);
        return OperateResult.success();
    }

    /**
     * 递归获取
     */
    public String queryMenus(String menuId, HttpServletRequest request){
        StringBuilder textbookString = new StringBuilder("");
        List<Menu> menus = queryLists(menuId,null,null);
        if(menus.size() > 0){
            for(Menu menu : menus){
                String classValue = "";
                if(!menu.getParentId().equals("0")){
                    classValue = "child-of-node-" + menu.getParentId();
                }
                String textbookStr = "<tr id='node-"+menu.getId()+"' class="+classValue+">" +
                        "<td style='padding-left:20px;'><input style=\"display: none\"  type=\"checkbox\" class=\"js-check\" data-yid=\"js-check-y\" data-xid=\"js-check-x\" name=\"ids[]\" checked value="+ menu.getId() +"><input name='listorders" + menu.getId() + "' type='text' size='3' value='"+ menu.getListorder() +"' class='input input-order'></td>" +
//                        "<td>" + menu.getId() + "</td>" +
                        "<td>" + "/" + menu.getModel() + "/" + menu.getAction() + "</td>" +
                        "<td>" + menu.getName() + "</td>" +
                        "<td>" + menu.getStatus().getName() + "</td>" +
                        "<td>" +
                        "<a href=" + request.getContextPath() + "/Menu/add?menuId=" + menu.getId() + ">添加下一级</a> | " +
                        "<a href=" + request.getContextPath() + "/Menu/update?id=" + menu.getId() + ">编辑</a> | " +
                        "<a class=\"js-ajax-delete\" href=" + request.getContextPath() + "/Menu/delete?id=" + menu.getId() + ">删除</a>" +
                        "</td>" +
                        "</tr>"+queryMenus(menu.getId(), request);
                textbookString.append(textbookStr);
            }
        }
        return textbookString.toString();
    }

    /**
     * 递归获取
     */
    public String queryMenus(String menuId,String roleId,int index){
        StringBuilder textbookString = new StringBuilder("");
        List<Menu> menus = menuRepository.findByParentId(menuId);
        if(menus.size() > 0){
            for(Menu menu : menus){
                String classValue = "";
                if(!menu.getParentId().equals("0")){
                    classValue = "child-of-node-" + menu.getParentId();
                }
                String checked = "";
                List<RolePower> rolePowers = rolePowerRepository.findByMenuIdAndRoleId(menu.getId(),roleId);
                if(rolePowers.size() > 0){
                    checked = "checked";
                }
                String textbookStr = "<tr id='node-"+menu.getId()+"' class="+classValue+">" +
                        "<td style='padding-left:30px;'><input type='checkbox' name='menuId' value='"+menu.getId()+"' "+checked+" level='"+index+"' onclick='javascript:checknode(this);'>"+menu.getName()+"</td>" +
                        "</tr>"+queryMenus(menu.getId() + "",roleId,index+1);
                textbookString.append(textbookStr);
            }
        }
        return textbookString.toString();
    }

    public List<Menu> queryAdminMenus(List<String> menuIds,String menuId){
        List<Menu> menus = queryLists(menuId,Status.BLOCK,menuIds);
        if(menus.size() > 0){
            for (Menu menu : menus){
                menu.setMenus(queryAdminMenus(menuIds,menu.getId()));
            }
        }
        return menus;
    }
}
