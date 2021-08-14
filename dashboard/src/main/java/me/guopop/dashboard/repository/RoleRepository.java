package me.guopop.dashboard.repository;

import me.guopop.dashboard.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author guopop
 * @date 2021/4/4 21:28
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    /**
     * 根据名称查找角色
     *
     * @param name
     * @return
     */
    Optional<Role> findByName(String name);
}

