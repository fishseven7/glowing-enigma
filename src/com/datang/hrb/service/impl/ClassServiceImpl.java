package com.datang.hrb.service.impl;

import java.util.List;

import com.datang.hrb.dao.ClassDao;
import com.datang.hrb.service.ClassService;
import com.datang.hrb.vo.Class;

public class ClassServiceImpl implements ClassService {
	private ClassDao classDao = new ClassDao();
	@Override
	public int addClass(Class clazz) {
		return classDao.addClass(clazz);
	}
	@Override
	public List<Class> classList() {
		return classDao.classList();
	}

}
