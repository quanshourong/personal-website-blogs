package com.rong.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.rong.base.utils.IConstants;

import lombok.Getter;
import lombok.Setter;

/**
 * 基础实体信息，所有的实体都继承该对象（每个数据表都相同的字段）
 * 
 * @author qsr
 *
 */
@Setter
@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable, IConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 实体ID, 主键 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;

	/** 实体创建时间 */
	protected Date created = new Date();

	/** 实体更新时间 */
	protected Date modified;

	/** 实体删除标记，为false表示删除 */
	protected Boolean isActive = Boolean.TRUE;

	/** 备注信息 */
	protected String description;
}
