package com.rong.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rong.base.BaseService;
import com.rong.base.PageInfo;
import com.rong.base.utils.PageUtils;
import com.rong.dto.UserDto;
import com.rong.dto.UserSearchDto;
import com.rong.entity.User;
import com.rong.repository.UserRepository;

/**
 * 用户业务管理
 * 
 * @author qsr
 *
 */
@Service
@Transactional(readOnly = true)
public class UserService extends BaseService<User, Integer> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private UserRepository userRepository;

	/***
	 * 新增用户
	 * 
	 * @param userDto
	 *            入参
	 * @return 用户信息
	 */
	@Transactional(readOnly = false)
	public User create(UserDto userDto) {
		User user = new User();
		mapper(userDto, user);
		return save(user);
	}

	/**
	 * 用户登录
	 * 
	 * @param userDto
	 *            登录入参
	 * @return 用户信息
	 */
	public User login(UserDto userDto) {
		User user = userRepository.findByUsernameAndPasswordAndIsActive(userDto.getUsername(), userDto.getPassword(),
				Boolean.TRUE);
		return user;
	}

	/**
	 * 用户分页列表
	 * 
	 * @param searchParams
	 *            搜索参数
	 * @param pageInfo
	 *            分页信息
	 * @return 用户信息分页列表
	 */
	public Page<User> getPageList(Map<String, Object> searchParams, PageInfo pageInfo) {
		UserSearchDto searchDto = new UserSearchDto();
		mapper(searchParams, searchDto);
		Map<String, Object> searchmap = searchDto.getSearchParams();
		logger.info("用户管理的搜索的条件是={},排序的字段为={}", searchmap, pageInfo.getSortType());
		Page<User> page = getPage(searchmap, pageInfo.getNumber(), pageInfo.getSize(), Direction.DESC,
				pageInfo.getSortType().split(","));
		return page;
	}

	/**
	 * 根据搜索条件搜索所有符合条件的用户信息
	 * 
	 * @param searchParams
	 *            搜索参数
	 * @return 用户信息列表
	 */
	public List<User> getByParam(Map<String, Object> searchParams) {
		UserSearchDto searchDto = new UserSearchDto();
		mapper(searchParams, searchDto);
		logger.info("用户管理的搜索的参数是={}", searchDto);
		return findAll(PageUtils.buildSpec(searchDto.getSearchParams()));
	}

	public User details(Integer id) {
		return findOne(id).get();
	}
}
