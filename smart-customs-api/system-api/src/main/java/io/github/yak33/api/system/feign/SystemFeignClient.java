package io.github.yak33.api.system.feign;

import io.github.yak33.common.core.domain.R;
import io.github.yak33.api.system.dto.SysUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 系统服务 Feign 客户端
 *
 * @author ZHANGCHAO
 * @date 2026/01/28
 */
@FeignClient(name = "smart-customs-system", contextId = "systemFeignClient")
public interface SystemFeignClient {
    
    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("/system/user/info/{userId}")
    R<SysUserDto> getUserInfo(@PathVariable("userId") Long userId);
    
    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/system/user/info/username/{username}")
    R<SysUserDto> getUserInfoByUsername(@PathVariable("username") String username);
}
