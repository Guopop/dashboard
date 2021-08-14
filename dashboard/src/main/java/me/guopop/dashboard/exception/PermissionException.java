package me.guopop.dashboard.exception;

import me.guopop.dashboard.model.enums.ErrorEnum;

/**
 * @author guopop
 * @date 2021/4/4 21:55
 */
public class PermissionException extends BaseException {
    public PermissionException(ErrorEnum errorEnum) {
        super(errorEnum);
    }
}
