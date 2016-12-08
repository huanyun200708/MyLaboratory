package cn.com.hq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.inject.Inject;

import cn.com.hq.entity.Account;
import cn.com.hq.mapper.AccountMapper;
import cn.com.hq.service.UserService;

public class UserServiceTest {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserService us = (UserService)ctx.getBean("userService");
	}
	
	
}
