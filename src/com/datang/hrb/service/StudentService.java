package com.datang.hrb.service;

import java.util.List;

import com.datang.hrb.vo.Student;

public interface StudentService {
	public int addStudent(Student student);

	public List<Student> studentList();
}
