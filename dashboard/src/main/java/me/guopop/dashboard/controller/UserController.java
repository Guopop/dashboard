package me.guopop.dashboard.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.guopop.dashboard.model.query.UserFindQuery;
import me.guopop.dashboard.model.query.UserQuery;
import me.guopop.dashboard.model.vo.PageVO;
import me.guopop.dashboard.model.vo.UserVO;
import me.guopop.dashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author guopop
 * @date 2021/4/4 21:21
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("新增用户")
    @PostMapping
    public void add(@RequestBody @Valid UserQuery query) {
        userService.add(query);
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public void delete(@ApiParam("用户id") @PathVariable Long id) {
        userService.delete(id);
    }

    @ApiOperation("修改用户")
    @PutMapping("/{id}")
    public void update(@ApiParam("用户id") @PathVariable Long id,
                       @RequestBody @Valid UserQuery query) {
        userService.update(id, query);
    }

    @ApiOperation("用户查询")
    @GetMapping
    public PageVO<UserVO> query(@Valid UserFindQuery query) {
        return userService.query(query);
    }
}
