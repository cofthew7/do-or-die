package hk.edu.uic.doordie.server.model.dao;

import hk.edu.uic.doordie.server.model.dbc.DatabaseConnection;
import hk.edu.uic.doordie.server.model.vo.Comment;
import hk.edu.uic.doordie.server.model.vo.Todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CommentDAO {
	public boolean addComment(int todoId, int uid, String content)
			throws Exception {
		// 连接数据库
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		PreparedStatement pStatement = null;
		// 判断结果
		boolean isSuccess = false;
		// 插入语句
		String query = "INSERT INTO `do-or-die`.`Comment` (`id`, `todoId`, `uid`, `content`, `createDate`) "
				+ "VALUES (NULL, ?, ?, ?, CURRENT_TIMESTAMP);";
		try {
			// 执行搜索
			pStatement = conn.prepareStatement(query);
			pStatement.setInt(1, todoId);
			pStatement.setInt(2, uid);
			pStatement.setString(3, content);

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

	public List<Comment> getComments(int todoId) throws Exception {
		// 连接数据库
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		PreparedStatement pStatement = null;
		// 声明变量用于存取结果

		List<Comment> commentList = new LinkedList<Comment>();
		// 搜索语句
		String query = "SELECT *  FROM `Comment` WHERE `todoId` = ?";

		try {
			// 执行搜索
			pStatement = conn.prepareStatement(query);
			pStatement.setInt(1, todoId);
			ResultSet rs = pStatement.executeQuery();

			// 若有record符合条件，将结果封装进相应对象
			while (rs.next()) {
				Comment comment = new Comment();
				
				comment.setId(rs.getInt("id"));
				comment.setTodoid(rs.getInt("todoId"));
				comment.setUid(rs.getInt("uid"));
				comment.setContent(rs.getString("content"));
				comment.setCreatedDate(rs.getTimestamp("createDate"));

				commentList.add(comment);
			}
			// 判断是否为空
			if (commentList.size() != 0) {
				System.out.println("Successful!");
			} else {
				System.out.println("No record satisfied!");
				commentList = null;
			}
			// 关闭连接，返回结果
			rs.close();
			dbc.close();
			return commentList;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
