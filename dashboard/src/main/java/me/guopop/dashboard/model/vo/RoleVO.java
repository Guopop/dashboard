package me.guopop.dashboard.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author guopop
 * @date 2021/4/11 22:41
 */
@ApiModel("角色展示数据封装")
@Data
@Builder
public class RoleVO {

    @ApiModelProperty("角色id")
    private Long id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("对应权限")
    private List<PermissionVO> permissionVOList;
}
