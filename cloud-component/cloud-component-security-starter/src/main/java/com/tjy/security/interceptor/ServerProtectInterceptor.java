package com.tjy.security.interceptor;

import com.tjy.result.ResultData;
import com.tjy.security.properties.CloudSecurityProperties;
import com.tjy.security.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 校验请求是否经过网关
 */
public class ServerProtectInterceptor implements HandlerInterceptor {

    private CloudSecurityProperties properties;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler){

        if (!properties.getOnlyFetchByGateway()) {
            return true;
        }

        String token = request.getHeader("gateway_token_header");

        String gatewayToken = new String(Base64Utils.encode("gateway_token_value".getBytes()));

        if (StringUtils.equals(gatewayToken, token)) {
            return true;
        } else {
            ResultData<String> resultData = new ResultData<>();
            resultData.setSuccess(false);
            resultData.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resultData.setMessage("请通过网关访问资源");
            WebUtils.writeJson(response,resultData);
            return false;
        }
    }

    public void setProperties(CloudSecurityProperties properties) {
        this.properties = properties;
    }
}