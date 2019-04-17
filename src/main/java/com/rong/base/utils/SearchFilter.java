package com.rong.base.utils;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

/**
 * 构造spring JPA的搜索条件
 * @author qsr
 *
 */
public class SearchFilter {

	/**
	 * 数据搜索的枚举值，含义<br>
	 * EQ 等于，相当于SQL：username=xxx<br>
	 * NE 不等于，相当于SQL：username !=xxx<br>
	 * LIKE 模糊搜索，相当于SQL：username like '%xxx%'<br>
	 * GT 大于，相当于于SQL：age > xx <br>
	 * LT 小于，相当于SQL：age < xx <br>
	 * GTE 大于等于，相当于SQL：age >=xx<br>
	 * LTE 小于等于，相当于SQL：age <=xx<br>
	 * IN 在其中，相当于SQL：age in (xx)<br>
	 * NOTIN 不在当中，相当于SQL：age not in (xx)<br>
	 * ISNULL 为空，相当于SQL：username is null<br>
	 * ISNOTNULL 不为空，相当于SQL：username is not null<br>
	 * 
	 * @author qsr
	 *
	 */
	public enum Operator {
		EQ, NE, LIKE, GT, LT, GTE, LTE, IN,NOTIN,ISNULL, ISNOTNULL, BETWEENDATE, BETWEENLONG
	}

	public String fieldName;
	public Object value;
	public Operator operator;

	/**
	 * 使用：EQ, LIKE, GT, LT, GTE, LTE 时调用
	 * 
	 * @param fieldName
	 * @param operator
	 * @param value
	 */
	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * 使用：IN, BETWEEN_DATE, BETWEEN_LONG 时调用
	 * 
	 * @param fieldName 搜索的字段名
	 * @param operator 搜索的方式
	 * @param values 搜索的值
	 */
	public SearchFilter(String fieldName, Operator operator, Object... values) {
		this.fieldName = fieldName;
		this.value = values;
		this.operator = operator;
	}

	/**
	 * searchParams中key的格式为OPERATOR_FIELDNAME
	 */
	public static Map<String, SearchFilter> parse(
			Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = Maps.newHashMap();
		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			if (StringUtils.isBlank(value.toString())) {
				continue;
			}

			// 拆分operator与filedAttribute
			String[] names = StringUtils.split(key, "_");
			if (names.length < 2) {
				throw new IllegalArgumentException(key
						+ " is not a valid search filter name");
			}

			String filedName = key.substring(names[0].length() + 1).replaceAll(
					"_", ".");
			Operator operator = Operator.valueOf(names[0]);

			// 创建searchFilter
			SearchFilter filter = new SearchFilter(filedName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}
}
