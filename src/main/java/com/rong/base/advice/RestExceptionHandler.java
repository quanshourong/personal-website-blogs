package com.rong.base.advice;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.rong.base.utils.IConstants;
import com.rong.base.utils.ResponseDto;

import lombok.extern.slf4j.Slf4j;

/***
 * 统一封装异常处理
 * 
 * @author XY
 *
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler implements ResponseBodyAdvice<Object> {

	// 具体的微服务，采用注入的方式
	@Value("${spring.application.name:}")
	private String serviceName;

	// ResponseDto 的全路径名
	private String responseClassPath = "com.rong.base.utils.ResponseDto";

	@ExceptionHandler(value = Exception.class)
	public ResponseDto<?> resolveException(HttpServletRequest request, HttpServletResponse response, Exception e) {
		log.error("调用={}服务出现异常了，请求的url是={}，请求的方法是={}，原因={}", serviceName, request.getRequestURL(), request.getMethod(),
				e);

		int errorCode = 500; // 默认错误码都是500
		if (e instanceof NullPointerException) {
			errorCode = 400; // 空指针大都是由请求参数造成的，返回400错误
		}
		// else if (e instanceof AccessDeniedException) {
		// errorCode = 403; // spring security 安全校验不通过
		// }
		return ResponseDto.error(errorCode, IConstants.MSG_ERROR);
	}

	/***
	 * 统一封装返回值
	 */
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType,
			Class<? extends HttpMessageConverter<?>> arg3, ServerHttpRequest arg4, ServerHttpResponse arg5) {
		// 获取返回值的类型
		final String returnTypeName = returnType.getParameterType().getName();
		if (Objects.equals("void", returnTypeName)) {
			return ResponseDto.success(null);
		}
		// 如果是ResponseDto的本身类，直接返回，不需要重新包装
		if (Objects.equals(responseClassPath, returnTypeName) || body instanceof ResponseDto) {
			return body;
		}

		return ResponseDto.success(body);
	}

	/**
	 * 是否支持响应重写，为true才会进入beforeBodyWrite方法
	 */
	@Override
	public boolean supports(MethodParameter arg0, Class<? extends HttpMessageConverter<?>> arg1) {
		return true;
	}
}
