package me.guopop.dashboard.util;

import cn.hutool.http.HttpStatus;
import me.guopop.dashboard.model.enums.ResultEnum;
import me.guopop.dashboard.model.vo.ResultVO;

/**
 * @author guopop
 * @date 2021/4/8 22:37
 */
public class ResultUtil {

    public static <T> ResultVO<T> success() {
        return ResultVO.generate(ResultEnum.OPERATE_SUCCESS.getCode(), ResultEnum.OPERATE_SUCCESS.getValue());
    }

    public static <T> ResultVO<T> success(T t) {
        return ResultVO.generate(HttpStatus.HTTP_OK, t);
    }

    public static <T> ResultVO<T> accept(ResultEnum resultEnum, String message) {
        return ResultVO.generate(resultEnum.getCode(), message);
    }
}
