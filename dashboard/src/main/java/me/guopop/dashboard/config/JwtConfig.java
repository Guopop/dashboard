package me.guopop.dashboard.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guopop
 * @date 2021/4/16 07:17
 */
@Getter
@Setter
public class JwtConfig {

    private String secret;

    private long expiration;
}
