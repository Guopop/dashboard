package me.guopop.dashboard.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author guopop
 * @date 2021/4/12 21:34
 */
@ApiModel("用户查询请求数据封装")
@Data
public class UserFindQuery extends PageQuery {

    @ApiModelProperty("用户名")
    private String name;
}
