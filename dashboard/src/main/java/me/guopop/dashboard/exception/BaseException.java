package me.guopop.dashboard.exception;

import me.guopop.dashboard.model.enums.ErrorEnum;

/**
 * @author guopop
 * @date 2021/4/4 21:55
 */
public class BaseException extends RuntimeException {
    public BaseException(ErrorEnum errorEnum) {
        super(errorEnum.getVal());
    }
}
