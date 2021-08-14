package me.guopop.dashboard.repository;

import me.guopop.dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author guopop
 * @date 2021/4/4 21:22
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    /**
     * 根据用户名查找用户
     *
     * @param name
     * @return
     */
    Optional<User> findByName(String name);
}
