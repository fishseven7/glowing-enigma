package com.datang.hrb.service;

import java.util.List;

import com.datang.hrb.vo.User;

public interface LoginService {
	public User login(User user);

	public List<User> getUserList();
}
