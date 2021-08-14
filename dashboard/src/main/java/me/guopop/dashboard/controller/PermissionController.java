package me.guopop.dashboard.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.guopop.dashboard.model.query.PermissionQuery;
import me.guopop.dashboard.model.vo.PermissionVO;
import me.guopop.dashboard.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author guopop
 * @date 2021/4/4 21:22
 */
@Api(tags = "权限管理")
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("新增权限")
    @PostMapping
    public void add(@RequestBody @Valid PermissionQuery query) {
        permissionService.add(query);
    }

    @ApiOperation("删除权限")
    @DeleteMapping("/{id}")
    public void delete(@ApiParam(value = "权限id", required = true) @PathVariable Long id) {
        permissionService.delete(id);
    }

    @ApiOperation("修改权限")
    @PutMapping("/{id}")
    public void update(@ApiParam(value = "权限id", required = true) @PathVariable Long id, @RequestBody @Valid PermissionQuery query) {
        permissionService.update(id, query);
    }

    @ApiOperation("查询权限")
    @GetMapping
    public List<PermissionVO> query() {
        return permissionService.query();
    }
}
