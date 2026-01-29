package io.github.yak33.common.web.config;

import jakarta.servlet.DispatcherType;
import io.github.yak33.common.web.config.properties.XssProperties;
import io.github.yak33.common.web.filter.RepeatableFilter;
import io.github.yak33.common.web.filter.XssFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Filter配置
 *
 * @author ZHANGCHAO
 */
@AutoConfiguration
@EnableConfigurationProperties(XssProperties.class)
public class FilterConfig {

    @Bean
    @ConditionalOnProperty(value = "xss.enabled", havingValue = "true")
    public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new XssFilter());
        registration.setName("xssFilter");
        registration.addUrlPatterns("/*");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE + 1);
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<RepeatableFilter> repeatableFilterRegistration() {
        FilterRegistrationBean<RepeatableFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RepeatableFilter());
        registration.setName("repeatableFilter");
        registration.addUrlPatterns("/*");
        return registration;
    }

}
