package me.guopop.dashboard.exception;

import me.guopop.dashboard.model.enums.ErrorEnum;

/**
 * @author guopop
 * @date 2021/1/25 14:31
 */
public class AuthenticationException extends BaseException {
    private static final long serialVersionUID = 3134461126760898795L;

    public AuthenticationException(ErrorEnum errorEnum) {
        super(errorEnum);
    }
}
