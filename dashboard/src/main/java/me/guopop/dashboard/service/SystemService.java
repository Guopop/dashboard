package me.guopop.dashboard.service;

import me.guopop.dashboard.model.query.AuthenticationQuery;
import me.guopop.dashboard.model.vo.AuthenticationVO;
import me.guopop.dashboard.model.vo.AuthorizationVO;

/**
 * @author guopop
 * @date 2021/4/16 07:22
 */
public interface SystemService {
    AuthenticationVO authenticate(AuthenticationQuery query);

    AuthorizationVO authorize();
}
