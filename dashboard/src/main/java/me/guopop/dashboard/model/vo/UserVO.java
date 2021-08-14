package me.guopop.dashboard.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author guopop
 * @date 2021/4/12 21:31
 */
@ApiModel("用户展示数据封装")
@Data
@Builder
public class UserVO {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名称")
    private String name;

    @ApiModelProperty("对应角色")
    private String role;

    @ApiModelProperty("状态")
    private Boolean state;
}
