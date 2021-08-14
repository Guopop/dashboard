package me.guopop.dashboard.handler;

import lombok.extern.slf4j.Slf4j;
import me.guopop.dashboard.model.enums.ResultEnum;
import me.guopop.dashboard.model.vo.ResultVO;
import me.guopop.dashboard.util.ResultUtil;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author guopop
 * @date 2021/4/8 22:46
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = "";
        if (bindingResult.hasErrors()) {
            message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        }
        log.error("请求参数错误: {}", message);
        return ResultUtil.accept(ResultEnum.PARAMETER_ERROR, message);
    }

    @ExceptionHandler(Exception.class)
    public ResultVO<?> exception(Exception e) {
        log.error(e.getMessage(), e);
        return ResultUtil.accept(ResultEnum.SYSTEM_ERROR, e.getMessage());
    }
}
