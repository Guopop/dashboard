package me.guopop.dashboard.model.enums;

import cn.hutool.http.HttpStatus;
import lombok.Getter;

/**
 * @author guopop
 * @date 2021/4/8 22:40
 */
@Getter
public enum ResultEnum {
    OPERATE_SUCCESS(HttpStatus.HTTP_OK, "操作成功"),
    PARAMETER_ERROR(HttpStatus.HTTP_BAD_REQUEST, "请求参数错误"),
    SYSTEM_ERROR(HttpStatus.HTTP_INTERNAL_ERROR, "系统异常"),
    ;

    private final int code;

    private final String value;

    ResultEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
