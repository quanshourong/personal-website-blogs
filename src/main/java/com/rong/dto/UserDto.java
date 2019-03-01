package com.rong.dto;

import lombok.Data;

/**
 * 用户信息的入参
 * 
 * @author XY
 *
 */
@Data
public class UserDto {

	/** 用户名 */
	private String username;

	/** 密码 */
	private String password;

	/** 邮箱 */
	private String email;
}
