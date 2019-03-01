package com.rong.base;

import com.rong.base.utils.IConstants;

import lombok.Data;

/**
 * 分页参数类型
 * 
 * @author qsr
 *
 */
@Data
public class PageInfo  implements IConstants {

	/** 当前分页页码 */
	private int number;
	/** 每页页长 */
	private int size;
	/** 排序类型 */
	private String sortType = AUTO;
	/** 总页码 */
	private Integer totalPages;

	/** 总记录数 */
	private Long totalElements;

	public PageInfo() {

	}

	/** 分页参数类型的构造函数 */
	public PageInfo(int pageNumber, int pageSize, String sortType) {
		super();
		this.number = pageNumber;
		this.size = pageSize;
		this.sortType = sortType;
	}	
}
