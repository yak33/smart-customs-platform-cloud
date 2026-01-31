package io.github.yak33.common.core.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 应用启动横幅打印工具
 * <p>
 * 为应用启动后提供美观、信息丰富的控制台输出,包含应用名称、版本、环境、访问地址等关键信息
 * </p>
 *
 * @author ZHANGCHAO
 */
@Slf4j
@UtilityClass
public class ApplicationStartupBanner {

    private static final String LINE = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
    private static final String DOUBLE_LINE = "═══════════════════════════════════════════════════════════════════════════════";

    /**
     * 打印应用启动成功信息
     *
     * @param env            Spring 环境配置
     * @param applicationName 应用名称
     * @param startTime      启动开始时间
     */
    public void printStartupBanner(Environment env, String applicationName, LocalDateTime startTime) {
        printStartupBanner(env, applicationName, startTime, null);
    }

    /**
     * 打印应用启动成功信息(完整版)
     *
     * @param env            Spring 环境配置
     * @param applicationName 应用名称
     * @param startTime      启动开始时间
     * @param asciiArt       ASCII 艺术字(可选)
     */
    public void printStartupBanner(Environment env, String applicationName, LocalDateTime startTime, String asciiArt) {
        try {
            String port = env.getProperty("server.port", "8080");
            String contextPath = env.getProperty("server.servlet.context-path", "");
            String profile = StrUtil.join(",", env.getActiveProfiles());
            if (StrUtil.isBlank(profile)) {
                profile = "default";
            }

            String localIp = NetUtil.getLocalhostStr();
            Duration duration = Duration.between(startTime, LocalDateTime.now());
            long seconds = duration.getSeconds();
            String startupTime = String.format("%d.%03ds", seconds, duration.toMillis() % 1000);

            // 拼接访问地址
            String localUrl = String.format("http://localhost:%s%s", port, contextPath);
            String externalUrl = String.format("http://%s:%s%s", localIp, port, contextPath);

            // 文档地址(如果是业务服务)
            String docUrl = null;
            if (applicationName.contains("业务") || applicationName.contains("系统")) {
                docUrl = String.format("http://localhost:%s%s/doc.html", port, contextPath);
            }

            // 构建输出
            StringBuilder banner = new StringBuilder("\n\n");
            banner.append(DOUBLE_LINE).append("\n");

            // ASCII 艺术字
            if (StrUtil.isNotBlank(asciiArt)) {
                banner.append(asciiArt).append("\n");
                banner.append(LINE).append("\n");
            }

            // 应用信息
            banner.append("  🚀 ").append(applicationName).append(" 启动成功!\n");
            banner.append(LINE).append("\n");

            // 环境信息
            banner.append(String.format("  📌 环境(Profile):      %s\n", profile));
            banner.append(String.format("  ⏱️  启动耗时:          %s\n", startupTime));
            banner.append(String.format("  🕐 启动时间:          %s\n", DateUtil.formatDateTime(DateUtil.date())));
            banner.append(String.format("  🌐 本地访问:          %s\n", localUrl));
            banner.append(String.format("  🌍 外部访问:          %s\n", externalUrl));

            // 文档地址
            if (StrUtil.isNotBlank(docUrl)) {
                banner.append(String.format("  📖 接口文档:          %s\n", docUrl));
            }

            banner.append(DOUBLE_LINE).append("\n");

            log.info(banner.toString());

        } catch (Exception e) {
            log.error("打印启动信息失败", e);
        }
    }

    /**
     * 获取 Gateway 的 ASCII 艺术字
     */
    public String getGatewayAscii() {
        return """
                   ____       _                          
                  / ___| __ _| |_ _____      ____ _ _   _ 
                 | |  _ / _` | __/ _ \\ \\ /\\ / / _` | | | |
                 | |_| | (_| | ||  __/\\ V  V / (_| | |_| |
                  \\____|\\__,_|\\__\\___| \\_/\\_/ \\__,_|\\__, |
                                                     |___/ 
                """;
    }

    /**
     * 获取 System 的 ASCII 艺术字
     */
    public String getSystemAscii() {
        return """
                  ____            _                 
                 / ___| _   _ ___| |_ ___ _ __ ___  
                 \\___ \\| | | / __| __/ _ \\ '_ ` _ \\ 
                  ___) | |_| \\__ \\ ||  __/ | | | | |
                 |____/ \\__, |___/\\__\\___|_| |_| |_|
                        |___/                        
                """;
    }

    /**
     * 获取 Business 的 ASCII 艺术字
     */
    public String getBusinessAscii() {
        return """
                  ____            _                     
                 | __ ) _   _ ___(_)_ __   ___  ___ ___ 
                 |  _ \\| | | / __| | '_ \\ / _ \\/ __/ __|
                 | |_) | |_| \\__ \\ | | | |  __/\\__ \\__ \\
                 |____/ \\__,_|___/_|_| |_|\\___||___/___/
                                                         
                """;
    }

    /**
     * 获取 Nacos 的 ASCII 艺术字
     */
    public String getNacosAscii() {
        return """
                  _   _                     
                 | \\ | | __ _  ___ ___  ___ 
                 |  \\| |/ _` |/ __/ _ \\/ __|
                 | |\\  | (_| | (_| (_) \\__ \\
                 |_| \\_|\\__,_|\\___\\___/|___/
                                            
                """;
    }
}
