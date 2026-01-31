/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.yak33.nacos;

import com.alibaba.nacos.sys.filter.NacosTypeExcludeFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

/**
 * Nacos 应用启动类
 * <p>
 * 使用 @SpringBootApplication 和 @ComponentScan，通过 CUSTOM 类型过滤器控制模块启用。
 * 注意：basePackages 必须为 com.alibaba.nacos，因为 Nacos 内部组件都在此包下。
 * </p>
 *
 * @author ZHANGCHAO
 */
@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = "com.alibaba.nacos", excludeFilters = {
    @Filter(type = FilterType.CUSTOM, classes = {NacosTypeExcludeFilter.class}),
    @Filter(type = FilterType.CUSTOM, classes = {TypeExcludeFilter.class}),
    @Filter(type = FilterType.CUSTOM, classes = {AutoConfigurationExcludeFilter.class})})
@ServletComponentScan
public class NacosApplication {

    public static void main(String[] args) {
        // true 单机模式 false 为集群模式 集群模式需搭配 cluster.conf 使用
        System.setProperty("nacos.standalone", "true");
        System.setProperty("server.tomcat.accesslog.enabled", "false");
        // 本地集群搭建：在各 nacos 目录下创建 conf/cluster.conf 文件
        // 注意：本地启动多个 nacos 时，home 目录不能相同
        // System.setProperty("nacos.home", "D:/nacos");
        
        SpringApplication.run(NacosApplication.class, args);
        
        printStartupBanner();
    }
    
    /**
     * 打印启动信息
     */
    private static void printStartupBanner() {
        String mode = "true".equals(System.getProperty("nacos.standalone", "false")) ? "单机模式" : "集群模式";
        String banner = "\n\n"
            + "===============================================================================\n"
            + "  _   _                     \n"
            + " | \\ | | __ _  ___ ___  ___ \n"
            + " |  \\| |/ _` |/ __/ _ \\/ __|\n"
            + " | |\\  | (_| | (_| (_) \\__ \\\n"
            + " |_| \\_|\\__,_|\\___\\___/|___/\n"
            + "                            \n"
            + "-------------------------------------------------------------------------------\n"
            + "  🚀 智慧关务-Nacos服务 (" + mode + ") 启动成功!\n"
            + "-------------------------------------------------------------------------------\n"
            + "  🌐 控制台访问: http://localhost:8848/nacos\n"
            + "  🔑 默认账号:     nacos / nacos\n"
            + "===============================================================================\n";
        log.info(banner);
    }
}
