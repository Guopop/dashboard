package me.guopop.dashboard.service;

import me.guopop.dashboard.model.query.RoleFindQuery;
import me.guopop.dashboard.model.query.RoleQuery;
import me.guopop.dashboard.model.vo.PageVO;
import me.guopop.dashboard.model.vo.RoleVO;

/**
 * @author guopop
 * @date 2021/4/4 21:29
 */
public interface RoleService {
    /**
     * 新增权限
     *
     * @param query
     */
    void add(RoleQuery query);

    /**
     * 删除权限
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 修改权限
     *
     * @param id
     * @param query
     */
    void update(Long id, RoleQuery query);

    /**
     * 查找权限
     *
     * @param query
     * @return
     */
    PageVO<RoleVO> query(RoleFindQuery query);
}
