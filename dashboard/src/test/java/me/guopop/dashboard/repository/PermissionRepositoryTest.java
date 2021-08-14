package me.guopop.dashboard.repository;

import me.guopop.dashboard.entity.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author guopop
 * @date 2021/4/4 18:50
 */
@SpringBootTest
class PermissionRepositoryTest {

    @Autowired
    private PermissionRepository permissionRepository;

    @Test
    void addPermission() {
        Permission p = Permission.builder().name("顶级目录").permissionOrder(1).type(Permission.Type.DIRECTORY).identifier("top").build();
        permissionRepository.save(p);
    }

    @Test
    void updatePermission() {
        Permission p = permissionRepository.findById(6L).orElseThrow(() -> new IllegalArgumentException("不存在"));
        p.setName("p2");
        p.setPermissionOrder(2);
        permissionRepository.save(p);
    }

}