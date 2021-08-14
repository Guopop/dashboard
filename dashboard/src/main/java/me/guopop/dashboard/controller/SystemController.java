package me.guopop.dashboard.controller;

import io.swagger.annotations.Api;
import me.guopop.dashboard.model.query.AuthenticationQuery;
import me.guopop.dashboard.model.vo.AuthenticationVO;
import me.guopop.dashboard.model.vo.AuthorizationVO;
import me.guopop.dashboard.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author guopop
 * @date 2021/4/16 07:17
 */
@Api(tags = "系统操作")
@RestController
public class SystemController {

    @Autowired
    private SystemService systemService;

    @PostMapping("/login")
    public AuthenticationVO authenticate(@RequestBody @Valid AuthenticationQuery query) {
        return systemService.authenticate(query);
    }

    @GetMapping("/auth")
    public AuthorizationVO authorize() {
        return systemService.authorize();
    }
}
