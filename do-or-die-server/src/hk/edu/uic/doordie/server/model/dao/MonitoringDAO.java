package hk.edu.uic.doordie.server.model.dao;

import hk.edu.uic.doordie.server.model.dbc.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MonitoringDAO {
	public boolean addMonitor(int todoId, int uid) throws Exception {
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		PreparedStatement pStatement = null;
		// 结果
		boolean isSuccess = false;
		// 搜索语句
		String query = "INSERT INTO `do-or-die`.`Monitoring` (`id`, `todoId`, `uid`) VALUES (NULL, ?, ?);";

			try {
				// 执行插入
				pStatement = conn.prepareStatement(query);
				pStatement.setInt(1, todoId);
				pStatement.setInt(2, uid);

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
