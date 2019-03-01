package com.rong.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Optional;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * 用户管理的搜索参数
 * 
 * @author XY
 *
 */
public class UserSearchDto {

	private String username;

	private String email;

	private String startDate; // 开始时间

	private String endDate; // 结束时间

	/**
	 * 获取查询条件与值
	 * 
	 * @param name
	 */
	public Map<String, Object> getSearchParams() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 注意格式化的表达式
		Map<String, Object> map = Maps.newHashMap();

		if (!Strings.isNullOrEmpty(this.email)) {
			putNoNull("LIKE_email", this.email, map);
		}
		if (!Strings.isNullOrEmpty(this.username)) {
			putNoNull("LIKE_username", this.username, map);
		}
		putNoNull("EQ_isActive", Boolean.TRUE, map);
		// 开始时间大于选定的时间
		if (!Strings.isNullOrEmpty(this.startDate)) {
			try {
				putNoNull("GTE_created", format.parse(startDate + " 00:00:00"), map);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		// 时间小于选定的时间
		if (!Strings.isNullOrEmpty(this.endDate)) {
			try {
				putNoNull("LTE_created", format.parse(endDate + " 23:59:59"), map);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public void putNoNull(String key, Object value, Map<String, Object> map) {
		Optional.ofNullable(value).ifPresent(a -> map.put(key, a));
	}

}
