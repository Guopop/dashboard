package me.guopop.dashboard.exception;

import me.guopop.dashboard.model.enums.ErrorEnum;

/**
 * @author guopop
 * @date 2021/4/12 21:18
 */
public class UserException extends BaseException {
    public UserException(ErrorEnum errorEnum) {
        super(errorEnum);
    }
}
