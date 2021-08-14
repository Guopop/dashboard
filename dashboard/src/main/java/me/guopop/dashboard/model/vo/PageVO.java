package me.guopop.dashboard.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author guopop
 * @date 2021/4/10 16:00
 */
@Data
public class PageVO<T> {

    private Long count;

    private List<T> list;

    public PageVO(Long count, List<T> list) {
        this.count = count;
        this.list = list;
    }

    public static <T> PageVO<T> generate(Long count, List<T> list) {
        return new PageVO<>(count, list);
    }
}
