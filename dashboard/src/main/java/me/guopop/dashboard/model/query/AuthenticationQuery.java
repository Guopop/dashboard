package me.guopop.dashboard.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author guopop
 * @date 2021/1/15 15:59
 */
@ApiModel("认证请求数据封装")
@Data
public class AuthenticationQuery {

    @ApiModelProperty("用户名")
    @NotBlank(message = "请输入用户名")
    private String username;

    @ApiModelProperty("密码(MD5加密)")
    @NotBlank(message = "请输入密码")
    private String password;
}
