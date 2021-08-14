package me.guopop.dashboard.model.vo;

import lombok.Data;

/**
 * @author guopop
 * @date 2021/4/8 22:20
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;

    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultVO<T> generate(Integer code, String msg) {
        return new ResultVO<>(code, msg, null);
    }

    public static <T> ResultVO<T> generate(Integer code, T data) {
        return new ResultVO<>(code, null, data);
    }

    public static <T> ResultVO<T> generate(Integer code, String msg, T data) {
        return new ResultVO<>(code, msg, data);
    }
}
