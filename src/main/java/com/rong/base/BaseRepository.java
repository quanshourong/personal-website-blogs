package com.rong.base;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 基础JPA的Repository，继承JpaSpecificationExecutor
 * 用于构造jpa的查询参数
 * @author qsr
 *
 * @param <T> 泛型，数据库的实体
 * @param <ID> 数据库实体的主键
 */
@NoRepositoryBean
public abstract interface BaseRepository<T, ID extends Serializable>
		extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}
