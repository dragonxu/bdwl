package com.liaoin.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 用户角色表
 */
@Entity
@Table(name = "t_admin_role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRole {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    private String id;

    @ApiModelProperty(value = "角色")
    @OneToOne(targetEntity = Role.class)
    private Role role;

    @ApiModelProperty(value = "用户")
    @OneToOne(targetEntity = Admin.class)
    private Admin admin;
}
