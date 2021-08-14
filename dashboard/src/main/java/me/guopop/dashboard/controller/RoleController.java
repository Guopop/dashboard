package me.guopop.dashboard.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.guopop.dashboard.model.query.RoleFindQuery;
import me.guopop.dashboard.model.query.RoleQuery;
import me.guopop.dashboard.model.vo.PageVO;
import me.guopop.dashboard.model.vo.RoleVO;
import me.guopop.dashboard.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author guopop
 * @date 2021/4/4 21:22
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("新增角色")
    @PostMapping
    public void add(@RequestBody @Valid RoleQuery query) {
        roleService.add(query);
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/{id}")
    public void delete(@ApiParam(value = "角色id", required = true) @PathVariable Long id) {
        roleService.delete(id);
    }

    @ApiOperation("修改角色")
    @PutMapping("/{id}")
    public void update(@ApiParam(value = "角色id", required = true) @PathVariable Long id,
                       @RequestBody @Valid RoleQuery query) {
        roleService.update(id, query);
    }

    @ApiOperation("查询角色")
    @GetMapping
    public PageVO<RoleVO> query(@RequestBody(required = false) @Valid RoleFindQuery query) {
        return roleService.query(query);
    }
}
