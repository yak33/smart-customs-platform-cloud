package io.github.yak33.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 详细日志过滤器（可选，开发环境开启）
 *
 * <p>打印请求体和响应体内容，仅在 debug 级别启用
 *
 * @author ZHANGCHAO
 * @date 2026/02/01
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "gateway.logging.detailed", havingValue = "true")
public class DetailedLoggingFilter implements GlobalFilter, Ordered {

    private static final int MAX_BODY_LENGTH = 1000;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 仅处理 debug 级别请求
        if (!log.isDebugEnabled()) {
            return chain.filter(exchange);
        }

        String path = exchange.getRequest().getURI().getPath();

        // 排除文件上传等大流量请求
        if (isMultipartRequest(exchange.getRequest())) {
            log.debug("[BODY] {} - Multipart request, body skipped", path);
            return chain.filter(exchange);
        }

        // 包装请求和响应以读取 body
        ServerHttpRequest decoratedRequest = decorateRequest(exchange);
        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(decoratedRequest)
                .response(decorateResponse(exchange))
                .build();

        return chain.filter(mutatedExchange);
    }

    private ServerHttpRequest decorateRequest(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        return new ServerHttpRequestDecorator(request) {
            @Override
            public Flux<DataBuffer> getBody() {
                return super.getBody()
                        .doOnNext(buffer -> logRequestBody(buffer, exchange));
            }
        };
    }

    private void logRequestBody(DataBuffer buffer, ServerWebExchange exchange) {
        try {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            DataBufferUtils.release(buffer);

            String body = new String(bytes, StandardCharsets.UTF_8);
            String path = exchange.getRequest().getURI().getPath();

            log.debug("[REQUEST BODY] {}: {}", path, truncate(body));
        } catch (Exception e) {
            log.debug("[REQUEST BODY] Failed to log: {}", e.getMessage());
        }
    }

    private ServerHttpResponse decorateResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        String path = exchange.getRequest().getURI().getPath();

        return new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(org.reactivestreams.Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(buffer -> {
                        // 复制 buffer 数据用于日志记录，原 buffer 继续向下传递
                        return logResponseBody(buffer, path, exchange);
                    }));
                }
                return super.writeWith(body);
            }
        };
    }

    private DataBuffer logResponseBody(DataBuffer buffer, String path, ServerWebExchange exchange) {
        try {
            // 复制 buffer 内容（不修改原 buffer）
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);

            // 使用 bufferFactory 重新创建一个新的 DataBuffer 返回给下游
            DataBuffer newBuffer = exchange.getResponse().bufferFactory().wrap(bytes);

            String body = new String(bytes, StandardCharsets.UTF_8);
            log.debug("[RESPONSE BODY] {}: {}", path, truncate(body));

            return newBuffer;
        } catch (Exception e) {
            log.debug("[RESPONSE BODY] Failed to log: {}", e.getMessage());
            return buffer;
        }
    }

    private boolean isMultipartRequest(ServerHttpRequest request) {
        String contentType = request.getHeaders().getFirst("Content-Type");
        return contentType != null && contentType.startsWith("multipart/");
    }

    private String truncate(String str) {
        if (str == null) return "null";
        if (str.length() <= MAX_BODY_LENGTH) return str;
        return str.substring(0, MAX_BODY_LENGTH) + "... (" + str.length() + " chars total)";
    }

    @Override
    public int getOrder() {
        // 在 RequestLoggingFilter 之后执行
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
