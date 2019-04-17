package com.rong.base.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/***
 * 封装统一返回的Json数据结构
 * @author qsr
 *
 * @param <T>
 */
@Data
public class ResponseDto<T> {

	private int code = IConstants.SUCCESS; // 操作码，成功为200，其他为失败

	private String msg = IConstants.MSG_SUCCESS; // 操作信息

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	private ResponseDto(T data) {
		this.data = data;
	}

	private ResponseDto(int code,String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 请求成功的统一返回值，传入响应的数据即可
	 * @param data 响应的数据
	 * @return
	 */
	public static <T> ResponseDto<T> success(T data) {
		return new ResponseDto<>(data);
	}

	/**
	 * 错误的响应信息
	 * @param code 响应码
	 * @param msg 响应信息
	 * @return
	 */
	public static <T> ResponseDto<T> error(int code,String msg) {
		return new ResponseDto<>(code,msg);
	}
}
