package io.github.yak33.gateway.satoken;

import cn.dev33.satoken.dao.auto.SaTokenDaoBySessionFollowObject;
import cn.dev33.satoken.util.SaFoxUtil;
import io.github.yak33.common.core.constant.GlobalConstants;
import io.github.yak33.common.redis.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sa-Token 持久层实现（使用 Redisson）
 * 与 System 服务共享相同的 Redis 存储
 *
 * @author ZHANGCHAO
 * @date 2026/01/29
 */
@Component
public class SaTokenDaoRedisReactive implements SaTokenDaoBySessionFollowObject {

    @Value("${tenant.enable:false}")
    private boolean tenantEnable;

    private String normalizeKey(String key) {
        return tenantEnable ? GlobalConstants.GLOBAL_REDIS_KEY + key : key;
    }

    @Override
    public String get(String key) {
        return RedisUtils.getCacheObject(normalizeKey(key));
    }

    @Override
    public void set(String key, String value, long timeout) {
        if (timeout == 0 || timeout <= NOT_VALUE_EXPIRE) {
            return;
        }
        if (timeout == NEVER_EXPIRE) {
            RedisUtils.setCacheObject(normalizeKey(key), value);
        } else {
            RedisUtils.setCacheObject(normalizeKey(key), value, Duration.ofSeconds(timeout));
        }
    }

    @Override
    public void update(String key, String value) {
        String realKey = normalizeKey(key);
        if (RedisUtils.hasKey(realKey)) {
            RedisUtils.setCacheObject(realKey, value, true);
        }
    }

    @Override
    public void delete(String key) {
        RedisUtils.deleteObject(normalizeKey(key));
    }

    @Override
    public long getTimeout(String key) {
        long timeout = RedisUtils.getTimeToLive(normalizeKey(key));
        return timeout < 0 ? timeout : timeout / 1000 + 1;
    }

    @Override
    public void updateTimeout(String key, long timeout) {
        RedisUtils.expire(normalizeKey(key), Duration.ofSeconds(timeout));
    }

    @Override
    public Object getObject(String key) {
        return RedisUtils.getCacheObject(normalizeKey(key));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getObject(String key, Class<T> classType) {
        return (T) RedisUtils.getCacheObject(normalizeKey(key));
    }

    @Override
    public void setObject(String key, Object object, long timeout) {
        if (timeout == 0 || timeout <= NOT_VALUE_EXPIRE) {
            return;
        }
        if (timeout == NEVER_EXPIRE) {
            RedisUtils.setCacheObject(normalizeKey(key), object);
        } else {
            RedisUtils.setCacheObject(normalizeKey(key), object, Duration.ofSeconds(timeout));
        }
    }

    @Override
    public void updateObject(String key, Object object) {
        String realKey = normalizeKey(key);
        if (RedisUtils.hasKey(realKey)) {
            RedisUtils.setCacheObject(realKey, object, true);
        }
    }

    @Override
    public void deleteObject(String key) {
        RedisUtils.deleteObject(normalizeKey(key));
    }

    @Override
    public long getObjectTimeout(String key) {
        long timeout = RedisUtils.getTimeToLive(normalizeKey(key));
        return timeout < 0 ? timeout : timeout / 1000 + 1;
    }

    @Override
    public void updateObjectTimeout(String key, long timeout) {
        RedisUtils.expire(normalizeKey(key), Duration.ofSeconds(timeout));
    }

    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size, boolean sortType) {
        String pattern = normalizeKey(prefix) + "*" + keyword + "*";
        Collection<String> keys = RedisUtils.keys(pattern);
        List<String> list = new ArrayList<>(keys);
        return SaFoxUtil.searchList(list, start, size, sortType);
    }
}
