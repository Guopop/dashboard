package me.guopop.dashboard.exception;

import me.guopop.dashboard.model.enums.ErrorEnum;

/**
 * @author guopop
 * @date 2021/4/11 22:25
 */
public class RoleException extends BaseException {
    public RoleException(ErrorEnum errorEnum) {
        super(errorEnum);
    }
}
