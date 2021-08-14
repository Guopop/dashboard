package me.guopop.dashboard.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guopop
 * @date 2021/4/11 22:45
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("角色查找请求数据封装")
@Data
public class RoleFindQuery extends PageQuery {

    @ApiModelProperty("角色名称")
    private String name;
}
