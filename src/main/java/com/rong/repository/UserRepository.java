package com.rong.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.rong.base.BaseRepository;
import com.rong.entity.User;

@RepositoryRestResource(path = "user", itemResourceRel = "resource", collectionResourceRel = "resources")
public interface UserRepository extends BaseRepository<User, Integer> {

	User findByUsernameAndPasswordAndIsActive(String username, String password, Boolean isActive);
}
