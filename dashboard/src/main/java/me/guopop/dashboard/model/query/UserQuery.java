package me.guopop.dashboard.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author guopop
 * @date 2021/4/12 21:05
 */
@ApiModel("用户请求数据封装")
@Data
public class UserQuery {

    @ApiModelProperty("用户名称")
    @NotBlank(message = "用户名称为空")
    private String name;

    @ApiModelProperty("用户状态")
    @NotNull(message = "用户状态为空")
    private Boolean state;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户分配角色id集合")
    private List<Long> roleIds;
}
