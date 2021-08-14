package me.guopop.dashboard.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author guopop
 * @date 2021/4/16 07:17
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "project")
public class ProjectConfig {

    private JwtConfig jwt;

}
