package com.rong.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.rong.entity.User;

/***
 * 解决Spring Data Rest不暴露ID字段的问题。
 * 将需要返回实体主键的实体使用该方法将实体id导出到Json中
 * @author qsr
 *
 */
@Configuration
public class SpringDataRestConfig implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(User.class);
	}
}
