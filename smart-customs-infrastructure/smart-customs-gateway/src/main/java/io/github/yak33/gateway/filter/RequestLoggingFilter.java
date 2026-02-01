package io.github.yak33.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Gateway 请求日志过滤器
 *
 * <p>记录所有经过网关的请求与响应信息，包括：
 * <ul>
 *   <li>请求路径、方法、客户端IP</li>
 *   <li>目标服务、路由ID</li>
 *   <li>响应状态码、处理耗时</li>
 *   <li>User-Agent、Content-Type</li>
 * </ul>
 *
 * @author ZHANGCHAO
 * @date 2026/02/01
 */
@Slf4j
@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    /** 日志时间格式 */
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /** 请求开始时间属性键 */
    private static final String REQUEST_START_TIME = "requestStartTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 记录请求开始时间
        exchange.getAttributes().put(REQUEST_START_TIME, System.currentTimeMillis());

        ServerHttpRequest request = exchange.getRequest();
        String requestId = generateRequestId(request);

        // 记录请求进入日志
        logRequest(request, exchange, requestId);

        return chain.filter(exchange)
                .doFinally(signalType -> logResponse(exchange, requestId));
    }

    /**
     * 记录请求信息
     */
    private void logRequest(ServerHttpRequest request, ServerWebExchange exchange, String requestId) {
        String method = Optional.ofNullable(request.getMethod())
                .map(HttpMethod::name)
                .orElse("UNKNOWN");
        String path = request.getURI().getPath();
        String query = Optional.ofNullable(request.getURI().getQuery())
                .map(q -> "?" + q)
                .orElse("");
        String clientIp = getClientIp(request);
        String userAgent = request.getHeaders().getFirst(HttpHeaders.USER_AGENT);
        String contentType = request.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);

        // 获取路由信息（如果已确定）
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        String targetService = Optional.ofNullable(route)
                .map(Route::getUri)
                .map(URI::toString)
                .orElse("unknown");
        String routeId = Optional.ofNullable(route)
                .map(Route::getId)
                .orElse("unknown");

        log.info("[REQUEST] {} | {} {} | Client: {} | Service: {} | Route: {} | UA: {} | CT: {}",
                requestId,
                method,
                path + query,
                clientIp,
                targetService,
                routeId,
                truncate(userAgent, 50),
                contentType
        );
    }

    /**
     * 记录响应信息
     */
    private void logResponse(ServerWebExchange exchange, String requestId) {
        Long startTime = exchange.getAttribute(REQUEST_START_TIME);
        long duration = startTime != null ? System.currentTimeMillis() - startTime : -1;

        ServerHttpResponse response = exchange.getResponse();
        Integer statusCode = response.getStatusCode() != null
                ? response.getStatusCode().value()
                : null;

        String statusIndicator = getStatusIndicator(statusCode);

        log.info("[RESPONSE] {} | Status: {} | Duration: {}ms {}",
                requestId,
                statusCode,
                duration,
                statusIndicator
        );
    }

    /**
     * 生成请求ID
     */
    private String generateRequestId(ServerHttpRequest request) {
        // 优先使用请求头中的 X-Request-ID（便于链路追踪）
        String requestId = request.getHeaders().getFirst("X-Request-ID");
        if (requestId != null && !requestId.isEmpty()) {
            return requestId;
        }
        // 生成短ID
        return java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /**
     * 获取客户端真实IP
     */
    private String getClientIp(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();

        // 优先从代理头获取
        String ip = headers.getFirst("X-Forwarded-For");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip.split(",")[0].trim();
        }

        ip = headers.getFirst("X-Real-IP");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        // 直接连接地址
        return Optional.ofNullable(request.getRemoteAddress())
                .map(InetSocketAddress::getAddress)
                .map(java.net.InetAddress::getHostAddress)
                .orElse("unknown");
    }

    /**
     * 根据状态码返回指示符
     */
    private String getStatusIndicator(Integer statusCode) {
        if (statusCode == null) return "⚠️";
        if (statusCode >= 200 && statusCode < 300) return "✓";
        if (statusCode >= 300 && statusCode < 400) return "→";
        if (statusCode >= 400 && statusCode < 500) return "⚡";
        if (statusCode >= 500) return "✗";
        return "?";
    }

    /**
     * 截断长字符串
     */
    private String truncate(String str, int maxLength) {
        if (str == null) return "-";
        return str.length() > maxLength ? str.substring(0, maxLength) + "..." : str;
    }

    @Override
    public int getOrder() {
        // 最高优先级：最先进入，最后退出
        // 比 NettyRoutingFilter (Ordered.LOWEST_PRECEDENCE) 和 WriteResponseFilter 更早执行
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
