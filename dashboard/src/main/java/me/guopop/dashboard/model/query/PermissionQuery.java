package me.guopop.dashboard.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.guopop.dashboard.entity.Permission;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @author guopop
 * @date 2021/4/4 21:33
 */
@ApiModel("权限请求数据封装")
@Data
public class PermissionQuery {

    @ApiModelProperty(value = "权限名称", required = true)
    @NotBlank(message = "权限名称为空")
    private String name;

    @ApiModelProperty("权限标识符")
    private String identifier;

    @ApiModelProperty(value = "权限类型", required = true)
    @NotBlank(message = "权限类型为空")
    private Permission.Type type;

    @ApiModelProperty(value = "权限排序", required = true)
    @Positive(message = "权限排序错误")
    private Integer permissionOrder;

    @ApiModelProperty("权限图标")
    private String icon;

    @ApiModelProperty("页面路由")
    private String path;

    @ApiModelProperty(value = "父级权限id", required = true)
    @Positive(message = "父级权限id错误")
    private Long parentId;
}
