package com.tjy.log;

import reactor.core.publisher.Mono;

public interface AccessLogService {

    /**
     * 保存AccessLog
     * @param gatewayLog 请求响应日志
     * @return 响应日志
     */
    Mono<GatewayLog> saveAccessLog(GatewayLog gatewayLog);

}
