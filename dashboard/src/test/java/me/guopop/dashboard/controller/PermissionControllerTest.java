package me.guopop.dashboard.controller;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import me.guopop.dashboard.entity.Permission;
import me.guopop.dashboard.model.query.PermissionQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author guopop
 * @date 2021/4/6 22:05
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Slf4j
class PermissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addPermission() throws Exception {
        PermissionQuery permissionQuery = new PermissionQuery();
        permissionQuery.setParentId(2L);
        permissionQuery.setName("杯子管理");
        permissionQuery.setType(Permission.Type.MENU);
        permissionQuery.setPermissionOrder(20);

        String request = JSONUtil.toJsonStr(permissionQuery);

        mockMvc.perform(post("/permission")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request));
    }
}