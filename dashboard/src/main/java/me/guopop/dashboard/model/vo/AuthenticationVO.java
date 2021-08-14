package me.guopop.dashboard.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author guopop
 * @date 2021/1/15 16:02
 */
@ApiModel("认证返回数据封装")
@Data
@Builder
public class AuthenticationVO {

    @ApiModelProperty("认证token")
    private String token;
}
