package com.liaoin.dao;

import com.liaoin.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {

    Role findByName(String name);

    Role findByNameAndIdNot(String name, String id);
}
