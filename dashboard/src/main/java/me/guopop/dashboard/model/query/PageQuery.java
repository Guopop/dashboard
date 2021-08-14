package me.guopop.dashboard.model.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author guopop
 * @date 2021/4/11 22:47
 */
@Data
public class PageQuery {

    @ApiModelProperty("当前页")
    private Integer page = 1;

    @ApiModelProperty("单页显示数量")
    private Integer pageSize = 10;
}
