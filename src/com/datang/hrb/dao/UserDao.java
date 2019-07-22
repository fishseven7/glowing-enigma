package com.datang.hrb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.datang.hrb.vo.User;

public class UserDao {

	/**
	 * 1.增加用户数据
	 * 2.查询用户数据
	 * 3.删除
	 * 4.更新
	 */
	public User getUserByUsername(String username) {
		Connection conn = ConnectionUtil.getConnection();
		PreparedStatement ps=null;
		User user = null;
		try {
			ps = conn.prepareStatement("select * from user where username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {//有数据
				user = new User();
				user.setUsername(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setAge(rs.getInt(3));
				user.setTs(rs.getDate(4));
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
		return user;
	}

	public List<User> getUserList() {
		Connection conn = ConnectionUtil.getConnection();
		PreparedStatement ps=null;
		List<User> userList = new ArrayList<User>();
		try {
			ps = conn.prepareStatement("select * from user");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {//有数据
				User user = new User();
				user.setUsername(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setAge(rs.getInt(3));
				user.setTs(rs.getDate(4));
				userList.add(user);
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
		return userList;
	}

	public int addUser(User user) {
		Connection conn = ConnectionUtil.getConnection();
		PreparedStatement ps=null;
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into user(username,password,phone) values(?,?,?)");
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getPhone());
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
}
