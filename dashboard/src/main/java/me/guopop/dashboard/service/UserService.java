package me.guopop.dashboard.service;

import me.guopop.dashboard.model.query.UserFindQuery;
import me.guopop.dashboard.model.query.UserQuery;
import me.guopop.dashboard.model.vo.PageVO;
import me.guopop.dashboard.model.vo.UserVO;

/**
 * @author guopop
 * @date 2021/4/4 21:29
 */
public interface UserService {
    /**
     * 用户新增
     *
     * @param query
     */
    void add(UserQuery query);

    /**
     * 删除用户
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 修改用户
     *
     * @param id
     * @param query
     */
    void update(Long id, UserQuery query);

    /**
     * 用户查询
     *
     * @param query
     * @return
     */
    PageVO<UserVO> query(UserFindQuery query);
}
