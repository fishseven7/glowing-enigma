package com.datang.hrb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.datang.hrb.vo.Class;
import com.datang.hrb.vo.User;

public class ClassDao {
	
	public int addClass(Class clazz) {
		Connection conn = ConnectionUtil.getConnection();
		PreparedStatement ps=null;
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into class(name,profession,school) values(?,?,?)");
			ps.setString(1, clazz.getName());
			ps.setString(2, clazz.getProfession());
			ps.setString(3, clazz.getSchool());
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

	public List<Class> classList() {
		Connection conn = ConnectionUtil.getConnection();
		PreparedStatement ps=null;
		List<Class> classList = new ArrayList<Class>();
		try {
			ps = conn.prepareStatement("select * from class");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {//有数据
				Class clazz = new Class();
				clazz.setId(rs.getInt(1));
				clazz.setName(rs.getString(2));
				clazz.setProfession(rs.getString(3));
				clazz.setSchool(rs.getString(4));
				classList.add(clazz);
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
		return classList;
	}
}
