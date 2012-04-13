package hk.edu.uic.doordie.server.model.dao;

import hk.edu.uic.doordie.server.model.dbc.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RelationDAO {
	public boolean addRelation(int myId, int friendId) throws Exception {
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		PreparedStatement pStatement = null;
		// 结果
		boolean isSuccess = false;
		// 搜索语句
		String query = "INSERT INTO `do-or-die`.`Relation` (`myId`, `friendId`) VALUES (?, ?);";

			try {
				// 执行插入
				pStatement = conn.prepareStatement(query);
				pStatement.setInt(1, myId);
				pStatement.setInt(2, friendId);

				int row = pStatement.executeUpdate();
				// 判断是否为空
				if (row > 0) {
					// 若不为空，返回刚插入的对象
					System.out.println("follow Successful!");
					isSuccess = true;
				} else {
					System.out.println("follow faild!");
					isSuccess = false;
				}
				// 关闭连接，返回结果
				// rs.close();
				dbc.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return isSuccess;
	}
}
