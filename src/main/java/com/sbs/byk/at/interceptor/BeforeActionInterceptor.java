package com.sbs.byk.at.interceptor;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("beforeActionInterceptor") // 컴포넌트 이름 설정
public class BeforeActionInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Map<String, Object> param = new HashMap<>();

		Enumeration<String> parameterNames = request.getParameterNames();

		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			Object paramValue = request.getParameter(paramName);

			param.put(paramName, paramValue);
		}

		ObjectMapper mapper = new ObjectMapper();
		String paramJson = mapper.writeValueAsString(param);

		String requestUri = request.getRequestURI();
		String queryString = request.getQueryString();

		String requestUriQueryString = requestUri;
		if (queryString != null && queryString.length() > 0) {
			requestUriQueryString += "?" + queryString;
		}

		String urlEncodedRequestUriQueryString = URLEncoder.encode(requestUriQueryString, "UTF-8");

		request.setAttribute("requestUriQueryString", requestUriQueryString);
		request.setAttribute("urlEncodedRequestUriQueryString", urlEncodedRequestUriQueryString);
		request.setAttribute("param", param);
		request.setAttribute("paramJson", paramJson);


		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
