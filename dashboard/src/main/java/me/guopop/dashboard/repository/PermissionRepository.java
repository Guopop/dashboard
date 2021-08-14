package me.guopop.dashboard.repository;

import me.guopop.dashboard.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author guopop
 * @date 2021/4/4 18:49
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    /**
     * 根据权限名称查询权限
     *
     * @param name
     * @return
     */
    Optional<Permission> findByName(String name);

    /**
     * 根据权限标识查询权限
     *
     * @param identifier
     * @return
     */
    Optional<Permission> findByIdentifierAndType(String identifier, Permission.Type type);
}
