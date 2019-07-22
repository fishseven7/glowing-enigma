package com.datang.hrb.service.impl;

import java.util.List;

import com.datang.hrb.dao.UserDao;
import com.datang.hrb.service.LoginService;
import com.datang.hrb.util.MD5Util;
import com.datang.hrb.vo.User;

public class LoginServiceImpl implements LoginService {
	
	private UserDao userDao = new UserDao();
	@Override
	public User login(User user) {
		//1.根据用户名   获取用户数据---这个过程跟数据库
		User dbUser =  userDao.getUserByUsername(user.getUsername());
		if(dbUser!=null&&dbUser.getPassword()!=null&&dbUser.getPassword().equals(MD5Util.getMD5(user.getPassword()))) {
			return user;
		}else {
			return null;
		}
	}
	@Override
	public List<User> getUserList() {
		
		return userDao.getUserList();
	}

}
