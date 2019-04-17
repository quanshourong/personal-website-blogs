package com.rong.base.utils;

import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageUtils<T> {

	/**
	 * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
	 * 
	 * 返回的结果的Parameter名已去除前缀.
	 */
	public static Map<String, Object> getParamStartWith(ServletRequest request, String prefix) {
		log.debug("根据条件获取搜索参数开始...");
		Enumeration<String> paramNames = request.getParameterNames();
		Map<String, Object> params = Maps.newTreeMap();
		prefix = Optional.ofNullable(prefix).orElseGet(() -> "");
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	/**
	 * 组合Parameters生成Query String的Parameter部分, 并在paramter name上加上prefix.
	 * 
	 * @see #getParametersStartingWith
	 */
	public static String enParamStrWithPrefix(Map<String, Object> params, String prefix) {
		if (params == null || params.isEmpty()) {
			return "";
		}
		prefix = Optional.ofNullable(prefix).orElseGet(() -> "");
		StringBuilder queryStringBuilder = new StringBuilder();
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			queryStringBuilder.append(prefix).append(entry.getKey()).append('=').append(entry.getValue());
			if (it.hasNext()) {
				queryStringBuilder.append('&');
			}
		}
		return queryStringBuilder.toString();
	}

	/**
	 * 构造简易搜索标准查询
	 * 
	 * @param searchParams
	 * @return
	 */
	public static <T> Specification<T> buildSimpleSpec(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		return bySearchFilter(filters.values());
	}

	/**
	 * 构造一般搜索标准查询
	 * 
	 * @param searchParams
	 * @return
	 */
	public static <T> Specification<T> buildSpec(Map<String, Object> searchParams) {

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		return bySearchFilter(filters.values());
	}

	/**
	 * 构造搜索标准查询，涉及Validate状态值（撤销中，正常）
	 * 
	 * @param searchParams
	 * @return
	 */
	public static <T> Specification<T> buildSpecIn(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		return bySearchFilter(filters.values());
	}

	/**
	 * 实现复杂对象查询，实现toPredicate方法，用JPA去构造Specification对象查询；
	 * 
	 * @author qsr
	 *
	 */
	public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters) {
		return new Specification<T>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 7054380594285260439L;

			// Root 查询中的条件表达式
			// CriteriaQuery 条件查询设计器
			// CriteriaBuilder 条件查询构造器
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (StringUtils.isNotEmpty(filters.toString())) {
					// 保存查询条件集
					List<Predicate> predicates = Lists.newArrayList();
					filters.forEach(f -> {
						String[] names = StringUtils.split(f.fieldName, ".");
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}
						switch (f.operator) {
						case EQ:
							predicates.add(builder.equal(expression, f.value));
							break;
						case NE:
							predicates.add(builder.notEqual(expression, f.value));
							break;
						case LIKE:
							predicates.add(builder.like(expression, "%" + f.value + "%"));
							break;
						case GT:
							predicates.add(builder.greaterThan(expression, (Comparable) f.value));
							break;
						case LT:
							predicates.add(builder.lessThan(expression, (Comparable) f.value));
							break;
						case GTE:
							predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) f.value));
							break;
						case LTE:
							predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) f.value));
							break;
						case IN:
							// 使用IN的查询，需要以数组为Value;
							predicates.add(builder.isTrue(expression.in((Object[]) f.value)));
							break;
						case BETWEENDATE:
							// 使用IN的查询，需要以数组为Value;
							predicates.add(builder.between(expression, (Date) ((List<Date>) f.value).get(0),
									(Date) ((List<Date>) f.value).get(1)));
							break;
						case BETWEENLONG:
							// 使用IN的查询，需要以数组为Value;
							predicates.add(builder.between(expression, (Long) ((List<Long>) f.value).get(0),
									(Long) ((List<Long>) f.value).get(1)));
							break;
						case ISNULL:
							predicates.add(builder.isNull(expression));
							break;
						case ISNOTNULL:
							predicates.add(builder.isNotNull(expression));
							break;
						default:
							break;
						}
					});

					// 将所有条件用 and 联合起来
					if (predicates.size() > 0) {
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}

				return builder.conjunction();
			}
		};
	}

}