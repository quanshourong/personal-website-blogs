package com.rong.base.advice;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.rong.base.utils.OperationDto;
import com.rong.base.utils.ResponseDto;

/***
 * 统一封装异常处理
 * 
 * @author XY
 *
 */
@RestControllerAdvice
public class RestExceptionHandler implements ResponseBodyAdvice<Object> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${spring.application.name:}")
	private String serviceName;

	@ExceptionHandler(value = Exception.class)
	public ResponseDto<?> resolveException(HttpServletRequest request, HttpServletResponse response, Exception e) {
		logger.error("调用={}服务出现异常了，请求的url是={}，请求的方法是={}，原因={}", serviceName, request.getRequestURL(),
				request.getMethod(), e);

		int errorCode = 500; // 默认错误码都是500
		if (e instanceof NullPointerException) {
			errorCode = 400; // 空指针大都是由请求参数造成的，返回400错误
		}
		// else if (e instanceof AccessDeniedException) {
		// errorCode = 403; // spring security 安全校验不通过
		// }
		return ResponseDto.error(errorCode, IConstants.ERRORMSG);
	}

	/***
	 * 统一封装返回值
	 */
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType,
			Class<? extends HttpMessageConverter<?>> arg3, ServerHttpRequest arg4, ServerHttpResponse arg5) {
		final String returnTypeName = returnType.getParameterType().getName();
		if (Objects.equals("void", returnTypeName)) {
			return ResponseDto.success(null);
		}
		if (Objects.equals("com.rong.base.utils.ResponseDto", returnTypeName)) {
			return body;
		}
		if (body instanceof OperationDto) {
			OperationDto od = (OperationDto) body;
			return ResponseDto.error(od.getCode(), od.getMsg());
		}

		return ResponseDto.success(body);
	}

	@Override
	public boolean supports(MethodParameter arg0, Class<? extends HttpMessageConverter<?>> arg1) {
		return true;
	}
}
