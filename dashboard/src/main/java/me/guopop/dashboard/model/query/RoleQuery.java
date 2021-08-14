package me.guopop.dashboard.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author guopop
 * @date 2021/4/10 23:32
 */
@ApiModel("角色请求数据封装")
@Data
public class RoleQuery {

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称为空")
    private String name;

    @ApiModelProperty("当前角色分配权限id集合")
    private List<Long> permissionIds;
}
