package me.guopop.dashboard.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author guopop
 * @date 2021/4/10 16:06
 */
@ApiModel("权限展示数据封装")
@Data
@Builder
public class PermissionVO {

    @ApiModelProperty("权限id")
    private Long id;

    @ApiModelProperty("权限名称")
    private String name;

    @ApiModelProperty("权限标识符")
    private String identifier;

    @ApiModelProperty("权限类型")
    private String type;

    @ApiModelProperty("权限排序")
    private Integer permissionOrder;

    @ApiModelProperty("权限图标")
    private String icon;

    @ApiModelProperty("页面路由")
    private String path;

    @ApiModelProperty("父级权限id")
    private Long parentId;
}
