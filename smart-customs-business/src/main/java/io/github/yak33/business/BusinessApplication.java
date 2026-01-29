package io.github.yak33.business;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 业务服务启动类
 *
 * @author ZHANGCHAO
 * @date 2026/01/28
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "io.github.yak33.api")
@MapperScan("io.github.yak33.**.mapper")
@SpringBootApplication(scanBasePackages = "io.github.yak33")
public class BusinessApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }
}
