package me.guopop.dashboard.service;

import me.guopop.dashboard.model.query.PermissionQuery;
import me.guopop.dashboard.model.vo.PermissionVO;

import java.util.List;

/**
 * @author guopop
 * @date 2021/4/4 21:28
 */
public interface PermissionService {
    /**
     * 新增权限
     *
     * @param query
     */
    void add(PermissionQuery query);

    /**
     * 查询所有权限
     *
     * @return
     */
    List<PermissionVO> query();

    /**
     * 更新权限
     *
     * @param id
     * @param query
     */
    void update(Long id, PermissionQuery query);

    /**
     * 删除权限
     *
     * @param id
     */
    void delete(Long id);
}
