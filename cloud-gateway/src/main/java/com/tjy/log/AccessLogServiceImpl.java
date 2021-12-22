package com.tjy.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccessLogServiceImpl implements AccessLogService {
    @Autowired
    private AccessLogRepository accessLogRepository;

    @Override
    public Mono<GatewayLog> saveAccessLog(GatewayLog gatewayLog) {
		return accessLogRepository.insert(gatewayLog);
    }
}
