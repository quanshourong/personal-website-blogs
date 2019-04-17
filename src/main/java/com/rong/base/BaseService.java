package com.rong.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;
import com.rong.base.utils.PageUtils;
import com.rong.base.utils.SearchFilter;
import com.rong.base.utils.SearchFilter.Operator;
import com.rong.base.utils.UserUtil;

/**
 * 基础业务模型，用于实现基础的业务功能<br>
 * 本身也地带强大的查询参数构造
 * @author qsr
 *
 * @param <T> 数据库实体
 * @param <ID> 数据库实体的主键实体
 */
@Service
@Transactional(readOnly = true)
public abstract class BaseService<T, ID extends Serializable> {

	@Autowired
	protected BaseRepository<T, ID> baseRepository;

	public Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();

	public void setbaseRepository(BaseRepository<T, ID> baseRepository) {
		this.baseRepository = baseRepository;
	}

	/**
	 * 按照主键查询
	 * 
	 * @param id
	 *            主键
	 * @return 返回id对应的实体
	 */
	@Transactional(readOnly = true)
	public Optional<T> findOne(ID id) {
		return baseRepository.findById(id);
	}

	/**
	 * 保存单个实体
	 * 
	 * @param t
	 *            实体
	 * @return 返回保存的实体
	 */
	@Transactional(readOnly = false)
	public T save(T t) {
		UserUtil.stamp(t);
		return baseRepository.save(t);
	}

	/**
	 * 保存多个实体
	 * 
	 * @param List<t>
	 *            实体
	 * @return 返回保存的实体
	 */
	@Transactional(readOnly = false)
	public List<T> save(List<T> tlist) {
		tlist.forEach(t -> {
			UserUtil.stamp(t);
		});
		return baseRepository.saveAll(tlist);
	}

	/**
	 * 根据主键删除相应实体
	 * 
	 * @param id
	 *            主键
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Transactional(readOnly = false)
	public void delete(ID id) throws IllegalAccessException, InvocationTargetException {
		T t = findOne(id).get();
		BeanUtils.setProperty(t, "isActive", Boolean.FALSE);
		save(t);
	}

	/**
	 * 删除实体
	 * 
	 * @param t
	 *            实体
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Transactional(readOnly = false)
	public void delete(T t) throws IllegalAccessException, InvocationTargetException {
		BeanUtils.setProperty(t, "isActive", Boolean.FALSE);
		save(t);
	}

	/**
	 * 删除实体list
	 * 
	 * @param t
	 *            实体
	 */
	@Transactional(readOnly = false)
	public void delete(List<T> t) {
		t.forEach(a -> {
			try {
				BeanUtils.setProperty(t, "isActive", Boolean.FALSE);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		});
		save(t);
	}

	/**
	 * 统计实体总数
	 *
	 * @return 实体总数
	 */
	@Transactional(readOnly = true)
	public long count() {
		return baseRepository.count();
	}

	/**
	 * 查询所有实体
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return baseRepository.findAll();
	}

	/**
	 * 查询所有实体
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> findAll(Map<String, Object> searchParams, Direction direction, String... sortType) {
		return baseRepository.findAll(PageUtils.buildSpec(searchParams), new Sort(direction, sortType));
	}

	/**
	 * 获取分页
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<T> getPage(Map<String, Object> searchParams, int pageNumber, int pageSize, Direction direction,
			String... sortType) {

		return baseRepository.findAll(PageUtils.buildSpec(searchParams),
				PageRequest.of(pageNumber - 1, pageSize, new Sort(direction, sortType)));
	}

	/**
	 * 根据某个字段查询实体集合
	 * 
	 * @param param
	 * @param operator
	 * @param object
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> findByParam(String param, Operator operator, Object object) {
		return baseRepository
				.findAll(PageUtils.bySearchFilter(ImmutableList.of(new SearchFilter(param, operator, object),
						new SearchFilter("isActive", Operator.EQ, Boolean.TRUE))));
	}

	/**
	 * 根据某个字段查询实体集合(排序)
	 * 
	 * @param param
	 * @param operator
	 * @param object
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> findByParam(String param, Operator operator, Object object, Sort sort) {
		return baseRepository
				.findAll(PageUtils.bySearchFilter(ImmutableList.of(new SearchFilter(param, operator, object),
						new SearchFilter("isActive", Operator.EQ, Boolean.TRUE))), sort);
	}

	/**
	 * 根据某个字段查询单个实体
	 * 
	 * @param param
	 * @param operator
	 * @param object
	 * @return
	 */
	@Transactional(readOnly = true)
	public T getByParam(String param, Operator operator, Object object) {
		List<T> list = baseRepository
				.findAll(PageUtils.bySearchFilter(ImmutableList.of(new SearchFilter(param, operator, object),
						new SearchFilter("isActive", Operator.EQ, Boolean.TRUE))));
		return list.isEmpty() ? null : list.get(0);
	}

	/**
	 * 根据某个字段查询数量
	 * 
	 * @param param
	 * @param operator
	 * @param object
	 * @return
	 */
	@Transactional(readOnly = true)
	public long countByParam(String param, Operator operator, Object object) {
		return baseRepository.count(PageUtils.bySearchFilter(ImmutableList.of(new SearchFilter(param, operator, object),
				new SearchFilter("isActive", Operator.EQ, Boolean.TRUE))));
	}

	/**
	 * 根据查询条件获取所有
	 * 
	 * @param spec
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> findAll(Specification<T> spec) {
		return baseRepository.findAll(spec);
	}

	/**
	 * 根据查询条件获取
	 * 
	 * @param spec
	 * @return
	 */
	@Transactional(readOnly = true)
	public Optional<T> findOne(Specification<T> spec) {
		return (Optional<T>) baseRepository.findOne(spec);
	}

	/**
	 * 根据查询条件获取数量
	 * 
	 * @param spec
	 * @return
	 */
	@Transactional(readOnly = true)
	public long count(Specification<T> spec) {
		return baseRepository.count(spec);
	}

	/**
	 * 根据某个字段查询实体集合(所有状态下)
	 * 
	 * @param param
	 * @param operator
	 * @param object
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> findALLByParam(String param, Operator operator, Object object) {
		return baseRepository
				.findAll(PageUtils.bySearchFilter(ImmutableList.of(new SearchFilter(param, operator, object),
						new SearchFilter("isActive", Operator.EQ, Boolean.TRUE))));
	}

	/**
	 * 克隆对象属性值
	 */
	@Transactional(readOnly = true)
	public void mapper(Object source, Object destination) {
		mapper.map(source, destination);
	}

	/**
	 * 获取集合的子列表，第二个参数是获取的大小，从0开始
	 */
	@Transactional(readOnly = true)
	public List<T> getLimit(List<T> list, Integer limit) {
		return list.size() > limit ? list.subList(0, limit) : list;
	}

}
