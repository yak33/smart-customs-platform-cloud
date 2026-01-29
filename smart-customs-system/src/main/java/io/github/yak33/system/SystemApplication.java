package io.github.yak33.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 系统服务启动类
 *
 * @author ZHANGCHAO
 */
@EnableDiscoveryClient
@MapperScan({"io.github.yak33.system.mapper", "io.github.yak33.generator.mapper"})
@SpringBootApplication(scanBasePackages = {
    "io.github.yak33.system",
    "io.github.yak33.generator",
    "io.github.yak33.web",
    "io.github.yak33.common"
})
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

}