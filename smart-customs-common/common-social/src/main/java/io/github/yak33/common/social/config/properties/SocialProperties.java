package io.github.yak33.common.social.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Social 配置属性
 *
 * @author thiszhc
 */
@Data
@ConfigurationProperties(prefix = "justauth")
public class SocialProperties {

    /**
     * 授权类型
     */
    private Map<String, SocialLoginConfigProperties> type;

}
