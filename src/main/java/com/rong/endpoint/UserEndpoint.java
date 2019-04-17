package com.rong.endpoint;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rong.base.BaseEndpoint;
import com.rong.base.PageInfo;
import com.rong.base.utils.PageUtils;
import com.rong.dto.UserDto;
import com.rong.entity.User;
import com.rong.service.UserService;

/**
 * 用户管理端点
 * 
 * @author qsr
 *
 */
@RestController
@RequestMapping(value = "/user")
public class UserEndpoint extends BaseEndpoint {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@javax.annotation.Resource
	private UserService userService;

	/**
	 * 新增用户
	 * 
	 * @param userDto
	 * @return
	 */
	@PostMapping(value = "")
	public ResponseEntity<?> create(@RequestBody UserDto userDto) {

		User user = userService.create(userDto);
		return ResponseEntity.ok(new Resource<User>(user, getSelfLink(this.getClass(), user.getId())));
	}

	/**
	 * 用户详情接口
	 */
	@GetMapping(value = "/{id}")
	@Override
	public ResponseEntity<?> details(@PathVariable Integer id) {
		logger.info("获取用户详情的主键id为={}", id);
		User user = userService.details(id);
		return ResponseEntity.ok(new Resource<User>(user, getSelfLink(this.getClass(), user.getId())));
	}

	/**
	 * 用户分页信息查询
	 */
	@GetMapping(value = "")
	@Override
	public HttpEntity<PagedResources<Resource<?>>> getPage(
			@RequestParam(value = PAGE_NUM, defaultValue = PAGE_NUM_VAL) int pageNumber,
			@RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_VAL) int pageSize,
			@RequestParam(value = SORTTYPE, defaultValue = SORT_TYPE_VAL) String sortType, ServletRequest request) {

		// 获取搜索参数
		Map<String, Object> searchParams = PageUtils.getParamStartWith(request, SEARCH_PREFIX1);
		PageInfo pageInfo = new PageInfo(pageNumber, pageSize, sortType);
		Page<User> page = userService.getPageList(searchParams, pageInfo);
		return doPageInfo(pageNumber, pageSize, sortType, request, this.getClass(), page);
	}

	/**
	 * 登录接口
	 * 
	 * @param userDto
	 *            登录参数
	 * @return
	 */
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody UserDto userDto) {
		User user = userService.login(userDto);
		return ResponseEntity.ok(new Resource<User>(user, getSelfLink(this.getClass(), user.getId())));
	}

}
