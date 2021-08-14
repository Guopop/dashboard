package me.guopop.dashboard.model.enums;

import lombok.Getter;

/**
 * @author guopop
 * @date 2021/4/4 21:58
 */
@Getter
public enum ErrorEnum {
    // 公共错误
    ILLEGAL_ARGUMENT("非法参数"),

    // 权限错误
    PERMISSION_NAME_EXIST("权限名称已存在"),
    PERMISSION_IDENTIFIER_EXIST("权限标识符已存在"),
    PERMISSION_DIR_PARENT_IS_DIR("目录父级只能是目录"),
    PERMISSION_MENU_PARENT_IS_DIR("菜单父级只能是目录"),
    PERMISSION_BUTTON_PARENT_IS_MENU("按钮父级只能是菜单"),

    // 角色错误
    ROLE_NAME_EXIST("角色名称已存在"),
    ROLE_NO_EXIST("角色不存在"),

    // 用户错误
    USER_NAME_EXIST("用户名称已存在"),
    USER_NO_EXIST("用户不存在"),

    // 认证授权
    AUTH_USER_NO_EXIST("账号不存在"),
    ;

    private final String val;

    ErrorEnum(String val) {
        this.val = val;
    }
}
