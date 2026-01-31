package io.github.yak33.gateway.runner;

import io.github.yak33.common.core.utils.ApplicationStartupBanner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 网关服务启动后处理
 *
 * @author ZHANGCHAO
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class GatewayApplicationRunner implements ApplicationRunner {

    private final Environment environment;
    private final LocalDateTime startTime = LocalDateTime.now();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 打印启动信息
        ApplicationStartupBanner.printStartupBanner(
            environment, 
            "智慧关务-网关服务", 
            startTime,
            ApplicationStartupBanner.getGatewayAscii()
        );
    }
}
