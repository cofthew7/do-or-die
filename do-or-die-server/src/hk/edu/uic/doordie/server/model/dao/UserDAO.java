package hk.edu.uic.doordie.server.model.dao;

import hk.edu.uic.doordie.server.model.dbc.DatabaseConnection;
import hk.edu.uic.doordie.server.model.vo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDAO {

	public User login(String email, String password) throws Exception {
		// 连接数据库
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		PreparedStatement pStatement = null;
		// 声明变量用于存取结果
		User user = new User();
		// 搜索语句
		String query = "select * from User where email= ? and password= ?";

		try {
			// 执行搜索
			pStatement = conn.prepareStatement(query);
			pStatement.setString(1, email);
			pStatement.setString(2, password);
			ResultSet rs = pStatement.executeQuery();

			while (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setAvatar(rs.getString("avatar"));
			}
			// 判断是否为空
			if (user.getId() != 0) {
				System.out.println("login Successfully!");
			} else {
				System.out.println("login Faild!");
				user = null;
			}
			// 关闭连接，返回结果
			// rs.close();
			dbc.close();
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public User register(String email, String password) throws Exception {
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		PreparedStatement pStatement = null;
		// 结果
		User user = new User();
		// 搜索语句
		String query = "INSERT INTO User (id, email, password, avatar) "
				+ "VALUES (NULL, ?, ?, NULL)";
		String result = "select * from User where email=?";
		if (isUserExisted(email)) {
			return null;
		} else {
			try {
				// 执行插入
				pStatement = conn.prepareStatement(query);
				pStatement.setString(1, email);
				pStatement.setString(2, password);

				int row = pStatement.executeUpdate();
				// 判断是否为空
				if (row > 0) {
					// 若不为空，返回刚插入的对象
					System.out.println("Signup Successful!");
					pStatement = conn.prepareStatement(result);
					pStatement.setString(1, email);
					ResultSet rs = pStatement.executeQuery();
					if (rs.next()) {
						user.setId(rs.getInt("id"));
						user.setEmail(rs.getString("email"));
						user.setPassword(rs.getString("password"));
						user.setAvatar(rs.getString("avatar"));
					}
				} else {
					System.out.println("Signup faild!");
					user = null;
				}
				// 关闭连接，返回结果
				// rs.close();
				dbc.close();
				return user;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public boolean isUserExisted(String email) throws Exception {
		// TODO Auto-generated method stub
		// 连接数据库
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		PreparedStatement pStatement = null;
		// 声明变量用于存取结果
		boolean isExisted = false;
		// 搜索语句
		String query = "select * from User where email=\'" + email + "\'";
		try {
			// 执行搜索
			pStatement = conn.prepareStatement(query);
			ResultSet rs = pStatement.executeQuery();

			// 判断是否为空
			if (!rs.next()) {
				System.out.println("Not Existed!");
				isExisted = false;
			} else {
				System.out.println("Existed!");
				isExisted = true;
			}
			// 关闭连接，返回结果
			// rs.close();
			dbc.close();
			return isExisted;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isExisted;
	}

	public List<User> getFriends(int myId) throws Exception {
		// 连接数据库
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		PreparedStatement pStatement = null;
		// 声明变量用于存取结果
		List<User> userList = new LinkedList<User>();
		// 搜索语句
		String query = "SELECT * FROM User WHERE id in (select friendId from Relation where myId = ?)";
		try {
			// 执行搜索
			pStatement = conn.prepareStatement(query);
			pStatement.setInt(1, myId);
			ResultSet rs = pStatement.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setAvatar(rs.getString("avatar"));
				userList.add(user);
			}
			// 判断是否为空
			if (userList.size() != 0) {
				System.out.println("getFriends Successful!");
			} else {
				System.out.println("getFriends No record satisfied!");
				userList = null;
			}
			// 关闭连接，返回结果
			// rs.close();
			dbc.close();
			return userList;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<User> getUnknownUsers(int myId) throws Exception {
		// 连接数据库
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		PreparedStatement pStatement = null;
		// 声明变量用于存取结果
		List<User> userList = new LinkedList<User>();
		// 搜索语句
		String query = "SELECT * FROM `User` WHERE id != ? and id not in (select friendId from Relation where myid = ?)";
		try {
			// 执行搜索
			pStatement = conn.prepareStatement(query);
			pStatement.setInt(1, myId);
			pStatement.setInt(2, myId);
			ResultSet rs = pStatement.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setAvatar(rs.getString("avatar"));
				userList.add(user);
			}
			// 判断是否为空
			if (userList.size() != 0) {
				System.out.println("getFriends Successful!");
			} else {
				System.out.println("getFriends No record satisfied!");
				userList = null;
			}
			// 关闭连接，返回结果
			// rs.close();
			dbc.close();
			return userList;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public User getUser(int id) throws Exception {
		// 连接数据库
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		PreparedStatement pStatement = null;
		// 声明变量用于存取结果
		User user = new User();
		// 搜索语句
		String query = "SELECT *  FROM `User` WHERE `id` = ?";
		try {
			// 执行搜索
			pStatement = conn.prepareStatement(query);
			pStatement.setInt(1, id);
			ResultSet rs = pStatement.executeQuery();

			while (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setAvatar(rs.getString("avatar"));
			}
			// 判断是否为空
			if (user.getId() != 0) {
				System.out.println("getUser Successful!");
			} else {
				System.out.println("getuser No record satisfied!");
				user = null;
			}
			// 关闭连接，返回结果
			// rs.close();
			dbc.close();
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
