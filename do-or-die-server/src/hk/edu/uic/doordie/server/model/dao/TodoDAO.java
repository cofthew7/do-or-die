package hk.edu.uic.doordie.server.model.dao;

import hk.edu.uic.doordie.server.model.dbc.DatabaseConnection;
import hk.edu.uic.doordie.server.model.vo.Todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class TodoDAO {

	public boolean addTodo(String name, Timestamp deadline, int isMonitored,
			int isFinished, int uid) throws Exception {
		// 连接数据库
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		PreparedStatement pStatement = null;
		// 判断结果
		boolean isSuccess = false;
		// 插入语句
		String query = "INSERT INTO `do-or-die`.`Todo` (`id`, `name`, `deadline`, `isMonitored`, `isFinished`, `createDate`, `uid`) "
				+ "VALUES (NULL, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?);";
		try {
			// 执行搜索
			pStatement = conn.prepareStatement(query);
			pStatement.setString(1, name);
			pStatement.setTimestamp(2, deadline);
			pStatement.setInt(3, isMonitored);
			pStatement.setInt(4, isFinished);
			pStatement.setInt(5, uid);

			int row = pStatement.executeUpdate();

			// 判断是否为空
			if (row > 0) {
				System.out.println("Successful!");
				isSuccess = true;
			} else {
				System.out.println("No record satisfied!");
				isSuccess = false;
			}
			// 关闭连接，返回结果
			// rs.close();
			dbc.close();
			return isSuccess;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean markFinish(int todoId) throws Exception {
		// 连接数据库
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		PreparedStatement pStatement = null;
		//
		boolean isSuccess = false;
		// 搜索语句
		String query = "UPDATE  `do-or-die`.`Todo` SET  `isFinished` =  '1' WHERE  `Todo`.`id` =?;";
		try {
			// 执行搜索
			pStatement = conn.prepareStatement(query);
			pStatement.setInt(1, todoId);
			int row = pStatement.executeUpdate();

			// 判断是否为空
			if (row > 0) {
				isSuccess = true;
				System.out.println("markfinish Successful!");
			} else {
				System.out.println("markfinish No record satisfied!");
			}
			// 关闭连接，返回结果
			// rs.close();
			dbc.close();
			return isSuccess;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Todo> getMyTodos(int myId) throws Exception {
		// 连接数据库
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		PreparedStatement pStatement = null;
		// 声明变量用于存取结果

		List<Todo> todoList = new LinkedList<Todo>();
		// 搜索语句
		String query = "SELECT * FROM  `Todo` WHERE  `uid` =?";

		try {
			// 执行搜索
			pStatement = conn.prepareStatement(query);
			pStatement.setInt(1, myId);
			ResultSet rs = pStatement.executeQuery();

			// 若有record符合条件，将结果封装进相应对象
			while (rs.next()) {
				Todo todo = new Todo();
				todo.setId(rs.getInt("id"));
				todo.setName(rs.getString("name"));
				todo.setDeadline(rs.getTimestamp("deadline"));
				todo.setUid(rs.getInt("uid"));
				todo.setIsMonitored(rs.getInt("isMonitored"));
				todo.setIsFinished(rs.getInt("isFinished"));
				todo.setCreatedDate(rs.getTimestamp("createDate"));

				todoList.add(todo);
			}
			// 判断是否为空
			if (todoList.size() != 0) {
				System.out.println("Successful!");
			} else {
				System.out.println("No record satisfied!");
				todoList = null;
			}
			// 关闭连接，返回结果
			rs.close();
			dbc.close();
			return todoList;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Todo> getMyMonitoringTodos(int myId) throws Exception {
		// 连接数据库
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		PreparedStatement pStatement = null;
		// 声明变量用于存取结果

		List<Todo> todoList = new LinkedList<Todo>();
		// 搜索语句
		String query = "SELECT * FROM  `Todo` WHERE id IN (SELECT todoId FROM Monitoring WHERE uid =?)";

		try {
			// 执行搜索
			pStatement = conn.prepareStatement(query);
			pStatement.setInt(1, myId);
			ResultSet rs = pStatement.executeQuery();

			// 若有record符合条件，将结果封装进相应对象
			while (rs.next()) {
				Todo todo = new Todo();
				todo.setId(rs.getInt("id"));
				todo.setName(rs.getString("name"));
				todo.setDeadline(rs.getTimestamp("deadline"));
				todo.setUid(rs.getInt("uid"));
				todo.setIsMonitored(rs.getInt("isMonitored"));
				todo.setIsFinished(rs.getInt("isFinished"));
				todo.setCreatedDate(rs.getTimestamp("createDate"));

				todoList.add(todo);
			}
			// 判断是否为空
			if (todoList.size() != 0) {
				System.out.println("Successful!");
			} else {
				System.out.println("No record satisfied!");
				todoList = null;
			}
			// 关闭连接，返回结果
			rs.close();
			dbc.close();
			return todoList;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
