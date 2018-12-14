package com.liaoin.dao;

import com.liaoin.bean.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,String>,JpaSpecificationExecutor<Menu> {

    List<Menu> findByParentId(String parentId);
}
