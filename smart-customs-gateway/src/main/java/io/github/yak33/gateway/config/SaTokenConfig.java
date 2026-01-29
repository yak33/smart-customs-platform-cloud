package io.github.yak33.gateway.config;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sa-Token 网关鉴权配置
 *
 * @author ZHANGCHAO
 * @date 2026/01/28
 */
@Configuration
public class SaTokenConfig {

    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截所有路径
                .addInclude("/**")
                // 排除不需要鉴权的路径
                .addExclude(
                        // 认证相关 - 不需要 token
                        "/auth/**",
                        "/captcha/**",
                        // 登录相关（兼容旧路径）
                        "/system/auth/login",
                        "/system/auth/logout",
                        "/system/auth/register",
                        "/system/captcha/**",
                        // 健康检查
                        "/actuator/**",
                        // Swagger 文档
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/doc.html",
                        "/webjars/**",
                        // 静态资源
                        "/favicon.ico",
                        "/*.html",
                        "/*.css",
                        "/*.js"
                )
                // 鉴权逻辑
                .setAuth(obj -> {
                    // 登录校验: 拦截所有请求，排除白名单
                    SaRouter.match("/**", r -> StpUtil.checkLogin());
                })
                // 异常处理
                .setError(e -> SaResult.error(e.getMessage()));
    }
}
