package com.datang.hrb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.datang.hrb.vo.Class;
import com.datang.hrb.vo.Student;

public class StudentDao {
	
	public int addStudent(Student student) {
		Connection conn = ConnectionUtil.getConnection();
		PreparedStatement ps=null;
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into student(code,name,classid,gender,email,phone) values(?,?,?,?,?,?)");
			ps.setString(1, student.getCode());
			ps.setString(2, student.getName());
			ps.setInt(3,  student.getClassId());
			ps.setString(4, student.getGender());
			ps.setString(5, student.getEmail());
			ps.setString(6, student.getPhone());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		return result;
	}

	public List<Student> studentList() {
		Connection conn = ConnectionUtil.getConnection();
		PreparedStatement ps=null;
		List<Student> studentList = new ArrayList<Student>();
		try {
			ps = conn.prepareStatement("SELECT * FROM student a LEFT JOIN class b ON a.classid=b.id");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {//有数据
				Student student = new Student();
				student.setCode(rs.getString(1));
				student.setName(rs.getString(2));
				student.setGender(rs.getString(4));
				student.setClassName(rs.getString(8));
				student.setProfession(rs.getString(9));
				student.setSchool(rs.getString(7));
				student.setEmail(rs.getString(5));
				student.setPhone(rs.getString(6));
				studentList.add(student);
			}
			} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		return studentList;
	}
}
