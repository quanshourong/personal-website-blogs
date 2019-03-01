package com.rong.base.utils;

import lombok.Data;

/**
 * 操作信息返回dto
 * 
 * @author qsr
 *
 */
@Data
public class OperationDto {

	private int code = IConstants.SUCCESS; // 操作码，成功为200，其他为失败
	private String msg = IConstants.SUCCESSMSG; // 操作信息

	/**
	 * 失败信息调用
	 */
	public void error() {
		this.code = IConstants.ERROR;
	}
}
