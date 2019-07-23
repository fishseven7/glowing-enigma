package com.datang.hrb.service.impl;

import java.util.List;

import com.datang.hrb.dao.StudentDao;
import com.datang.hrb.service.StudentService;
import com.datang.hrb.vo.Student;

public class StudentServiceImpl implements StudentService {
	StudentDao studentDao = new StudentDao();
	@Override
	public int addStudent(Student student) {
		return studentDao.addStudent(student);
	}
	@Override
	public List<Student> studentList() {
		return studentDao.studentList();
	}

}
