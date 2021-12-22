package com.tjy.auth.exception;

import com.tjy.result.ResultData;
import com.tjy.result.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

@Slf4j
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<ResultData<String>> translate(Exception e) throws Exception {
        log.error("认证服务器异常",e);

        ResultData<String> response = resolveException(e);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    /**
     * 构建返回异常
     * @param e exception
     * @return
     */
    private ResultData<String> resolveException(Exception e) {
        // 初始值 500
        ReturnCode returnCode = ReturnCode.RC500;
        int httpStatus = HttpStatus.UNAUTHORIZED.value();
        //不支持的认证方式
        if(e instanceof UnsupportedGrantTypeException){
            returnCode = ReturnCode.UNSUPPORTED_GRANT_TYPE;
			httpStatus = HttpStatus.FORBIDDEN.value();
        //用户名或密码异常
        }else if(e instanceof InvalidGrantException){
            returnCode = ReturnCode.USERNAME_OR_PASSWORD_ERROR;
			httpStatus = HttpStatus.FORBIDDEN.value();
        }

        ResultData<String> failResponse = ResultData.fail(httpStatus, returnCode.getMessage());
        failResponse.setStatus(httpStatus);

        return failResponse;
    }

}
