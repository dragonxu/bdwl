package com.liaoin.bean;

import com.liaoin.Enum.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_menu")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "uuid")
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "应用")
    private String app;

    @ApiModelProperty(value = "控制器")
    private String model;

    @ApiModelProperty(value = "方法")
    private String action;

    @ApiModelProperty(value = "参数")
    private String params;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态")
    private Status status;

    @ApiModelProperty(value = "上级菜单")
    private String parentId;

    @ApiModelProperty(value = "排序")
    private int listorder;

    @Transient
    @ApiModelProperty(value = "下级菜单列表")
    private List<Menu> menus;
}
