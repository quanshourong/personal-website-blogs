package com.rong.base;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletRequest;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.google.common.collect.Lists;
import com.rong.base.utils.DateUtils;
import com.rong.base.utils.IConstants;

/**
 * 基础控制器 其他控制器继承此控制器获得日期字段类型转换和防止XSS攻击的功能
 * 
 * @description
 * @author qsr
 * @date 2019年02月01日
 */
public abstract class BaseEndpoint implements IConstants {

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}

			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});

		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});

		// Timestamp 类型转换
		binder.registerCustomEditor(Timestamp.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				Date date = DateUtils.parseDate(text);
				setValue(date == null ? null : new Timestamp(date.getTime()));
			}
		});
	}

	/**
	 * 统一构造hal链接
	 * 
	 * @param controllerClass
	 *            控制器class，带有@Controller
	 * @param id
	 *            主键字段
	 * @return
	 */
	public Link getSelfLink(Class<?> controllerClass, Integer id) {

		return ControllerLinkBuilder
				.linkTo(((BaseEndpoint) ControllerLinkBuilder.methodOn(controllerClass)).details(id)).withSelfRel();
	}

	// 咨询信息分页列表
	public HttpEntity<PagedResources<Resource<?>>> page(int pageNumber, int pageSize, String sortType,
			ServletRequest request, Class<?> controllerClass, Page<? extends BaseEntity> page) {

		List<Resource<?>> list = Lists.newArrayList();
		page.getContent().forEach(item -> {
			list.add(new Resource<>(item, getSelfLink(controllerClass, item.getId())));
		});
		// 组装分页信息
		PageMetadata pageMetadata = new PageMetadata(pageSize, pageNumber, page.getTotalElements(),
				page.getTotalPages());
		// 第一页链接
		Link firstLink = ControllerLinkBuilder.linkTo(((BaseEndpoint) ControllerLinkBuilder.methodOn(controllerClass))
				.getPage(1, pageSize, sortType, request)).withRel("first");
		int totalPages = page.getTotalPages();
		Link prevLink = null;
		// 大于1时添加上一页链接
		if (pageNumber > 1 && totalPages > pageNumber) {
			prevLink = ControllerLinkBuilder.linkTo(((BaseEndpoint) ControllerLinkBuilder.methodOn(controllerClass))
					.getPage(pageNumber - 1, pageSize, sortType, request)).withRel("prev");
		}
		// 本页链接
		Link selfLink = ControllerLinkBuilder.linkTo(((BaseEndpoint) ControllerLinkBuilder.methodOn(controllerClass))
				.getPage(pageNumber, pageSize, sortType, request)).withSelfRel();

		// 当总页数大于当前页数时，才有下一页
		Link nextLink = null;
		if (totalPages > pageNumber) {
			// 下一页链接
			nextLink = ControllerLinkBuilder.linkTo(((BaseEndpoint) ControllerLinkBuilder.methodOn(controllerClass))
					.getPage(pageNumber + 1, pageSize, sortType, request)).withRel("next");
		}

		// 最后一页链接
		Link lastLink = ControllerLinkBuilder.linkTo(((BaseEndpoint) ControllerLinkBuilder.methodOn(controllerClass))
				.getPage(page.getTotalPages(), pageSize, sortType, request)).withRel("last");
		PagedResources<Resource<?>> pagedResources;

		// 判断是否存在上一页和下一页，返回的参数不能为空，总共存在4种情况
		if (Objects.nonNull(prevLink)) {
			if (Objects.nonNull(nextLink)) {
				pagedResources = new PagedResources<Resource<?>>(list, pageMetadata, firstLink, prevLink, selfLink,
						nextLink, lastLink);
			} else {
				pagedResources = new PagedResources<Resource<?>>(list, pageMetadata, firstLink, prevLink, selfLink,
						lastLink);
			}

		} else {
			if (Objects.nonNull(nextLink)) {
				pagedResources = new PagedResources<Resource<?>>(list, pageMetadata, firstLink, selfLink, nextLink,
						lastLink);
			} else {
				pagedResources = new PagedResources<Resource<?>>(list, pageMetadata, firstLink, selfLink, lastLink);
			}

		}
		return new HttpEntity<PagedResources<Resource<?>>>(pagedResources);
	}

	/**
	 * 抽象方法，由子类实现分页的数据的获取
	 * 
	 * @param pageNumber
	 *            查询的页码
	 * @param pageSize
	 *            分页大小
	 * @param sortType
	 *            排序字段
	 * @param request
	 *            请求参数
	 * @return
	 */
	protected abstract HttpEntity<PagedResources<Resource<?>>> getPage(int pageNumber, int pageSize, String sortType,
			ServletRequest request);

	/**
	 * 实体详情接口，交给子类实现
	 * 
	 * @param id
	 *            数据库主键id
	 * @return
	 */
	protected abstract ResponseEntity<?> details(Integer id);

}
