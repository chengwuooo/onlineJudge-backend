package com.chengwu.gateway.filter;


import cn.hutool.core.text.AntPathMatcher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class GlobalAuthFilter implements GlobalFilter, Ordered {
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 自定义网关过滤器，用于实现权限校验功能。
     * 当请求路径匹配"inner"时，认为是内部调用，此时如果未授权，则返回403 Forbidden。
     * 对于其他路径，正常传递请求到下一个过滤器。
     *
     * @param exchange 服务器web交换机，用于获取和设置HTTP请求和响应。
     * @param chain 过滤器链，用于继续或停止过滤器链的执行。
     * @return Mono<Void>，表示异步操作的结果。
     */

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求对象
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        // 获取请求路径
        String path = serverHttpRequest.getURI().getPath();

        // 判断请求路径是否为内部调用路径
        // 判断路径中是否包含 inner，只允许内部调用
        if (antPathMatcher.match("/**/inner/**", path)) {
            // 如果是内部调用但未授权，则设置响应状态码为403 Forbidden
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            // 创建数据缓冲工厂
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            // 将“无权限”消息转换为数据缓冲区
            DataBuffer dataBuffer = dataBufferFactory.wrap("无权限".getBytes(StandardCharsets.UTF_8));
            // 返回响应，写入数据缓冲区
            return response.writeWith(Mono.just(dataBuffer));
        }
        // 如果不是内部调用路径，则继续执行过滤器链
        // todo 统一权限校验，通过 JWT 获取登录用户信息
        return chain.filter(exchange);
    }



    /**
     * 优先级提到最高
     */
    @Override
    public int getOrder() {
        return 0;
    }
}