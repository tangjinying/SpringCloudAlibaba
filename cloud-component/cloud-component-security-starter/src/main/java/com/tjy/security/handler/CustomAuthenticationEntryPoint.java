package com.tjy.security.handler;

import com.tjy.result.ResultData;
import com.tjy.result.ReturnCode;
import com.tjy.security.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {
		response.setStatus(HttpStatus.FORBIDDEN.value());
		ResultData<String> resultData = ResultData.fail(ReturnCode.CLIENT_AUTHENTICATION_FAILED.getCode(), ReturnCode.CLIENT_AUTHENTICATION_FAILED.getMessage());
		WebUtils.writeJson(response,resultData);
	}
}
