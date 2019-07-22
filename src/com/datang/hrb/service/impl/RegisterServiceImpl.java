package com.datang.hrb.service.impl;

import com.datang.hrb.dao.UserDao;
import com.datang.hrb.service.RegisterService;
import com.datang.hrb.util.MD5Util;
import com.datang.hrb.vo.User;

public class RegisterServiceImpl implements RegisterService {
	private UserDao userDao = new UserDao();
	@Override
	public int addUser(User user) {
		user.setPassword(MD5Util.getMD5(user.getPassword()));
		return userDao.addUser(user);
	}

}
