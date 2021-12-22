package com.tjy.security.handler;

import com.tjy.result.ResultData;
import com.tjy.result.ReturnCode;
import com.tjy.security.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description
 */

@Service
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setStatus(ReturnCode.UNAUTHORIZED.getCode());
		ResultData<String> resultData = ResultData.fail(ReturnCode.UNAUTHORIZED.getCode(), ReturnCode.UNAUTHORIZED.getMessage());
		WebUtils.writeJson(response,resultData);
	}
}