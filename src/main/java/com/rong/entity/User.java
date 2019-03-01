package com.rong.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rong.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户信息实体
 * 
 * @author qsr
 *
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7736415827002014078L;

	/** 用户名 */
	@Column(unique = true)
	private String username;

	/** 密码 */
	@JsonIgnore
	private String password;

	/** 邮箱 */
	@Column(unique = true)
	private String email;

}
