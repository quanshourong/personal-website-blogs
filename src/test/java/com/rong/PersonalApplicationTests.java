package com.rong;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rong.entity.User;
import com.rong.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonalApplicationTests {

	@Resource
	private UserService userService;
	
	@Test
	public void contextLoads() {
		User user =new User();
		user.setEmail("1359859903@qq.com");
		user.setUsername("rong");
		user.setPassword("123456");
		user = userService.save(user);
		System.out.println(user);
	}

}
