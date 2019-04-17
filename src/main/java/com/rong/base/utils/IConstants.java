package com.rong.base.utils;

/**
 * 系统初始化常量
 * @author qsr
 *
 */
public interface IConstants {
	
	/** 编码格式UTF8 */
	String UTF8 = "UTF-8";

	/** 统计（日） */
	public String[] DAY = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
			"22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };

	/** 统计（月） */
	public String[] MONTH = { "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月" };
	
	/**日期格式*/
	String DATETIME_FORMAT="yyyy-MM-dd HH:mm";
	String DATETIME_MIN_FORMAT="yyyy-MM-dd HH:mm";
	String DATETIME_MS_FORMAT="yyyy-MM-dd HH:mm:sss";
	String DATE_FORMAT="yyyy-MM-dd";
	String TIME_FORMAT="HH:mm:ss";
	String[] FORMAT_PATTERNS= {DATETIME_FORMAT,DATETIME_MIN_FORMAT,DATE_FORMAT,TIME_FORMAT};
	
	
	
	// -------------------分页参数封装 START-----------------------

		/** 页面View相关输入输出常量名 */
		String PAGE_NUM = "number";
		/** 页面View相关输入输出常量名 */
		String PAGE_SIZE = "size";
		/** 页面View相关输入输出常量名 */
		String PAGELIST = "list";
		/** 页面View相关输入输出常量名 */
		String SORTTYPES = "sortTypes";
		String SORT_TYPE_VAL="id";
		/** 页面View相关输入输出常量名 */
		String SORTTYPE = "sortType";
		/** 页面View相关输入输出常量名 */
		String SEARCHPARAMS = "searchParams";

		/** 页面尺寸 */
		String PAGE_SIZE_VAL = "12";
		/** 默认首页 */
		String PAGE_NUM_VAL = "1";
		/** 搜索排序默认类型 */
		String AUTO = "auto";
		/** 搜索排序默认类型 */
		String AUTO_STR = "自动";
		/** 搜索默认前缀 */
		String SEARCH_PREFIX = "search_";
		/** 搜索默认前缀 */
		String SEARCH_PREFIX1 = "";

		// -------------------分页参数封装  END-----------------------
	
		
		String MSG_ERROR_USER = "用户已失效，请重新登录！";
		String MSG_ERROR_PERMISSION = "权限不足！";
		String MSG_SUCCESS = "操作成功！";
		String MSG_ERROR = "操作失败！";
		
		
	    
		/**操作成功标记*/
		int  SUCCESS =200;
		/**操作失败标记*/
		int  ERROR =1;
}
