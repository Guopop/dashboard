package me.guopop.dashboard.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @author guopop
 * @date 2021/1/16 10:44
 */
@ApiModel("授权返回数据封装")
@Data
@Builder
public class AuthorizationVO {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("授权权限列表")
    private Set<PermissionVO> permissions;

    @ApiModelProperty("授权标识符集合")
    private Set<String> identifiers;
}
