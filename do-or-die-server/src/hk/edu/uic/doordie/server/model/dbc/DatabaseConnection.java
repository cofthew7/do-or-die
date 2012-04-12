package hk.edu.uic.doordie.server.model.dbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
		
		public Connection conn = null;
		
		private static final String DBDRIVER = "org.gjt.mm.mysql.Driver";

		private static final String DBURL = "jdbc:mysql://localhost:3306/do-or-die";

		private static final String DBUSER = "root";
		private static final String DBPASSWORD = "root";
			
		public DatabaseConnection() throws Exception {
			try {
				Class.forName(DBDRIVER);			// Load database driver
				this.conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);		// Connect database
			} catch (Exception e) {
				throw e;
			}
		}
		
		public Connection getConnection() {		// Get the connection
			return this.conn;
		}	
		
		public void close() throws Exception {
			if (this.conn != null) {
				try {
					this.conn.close();			// Close database connection
				} catch (Exception e) {
					throw e;
				}
			}
		}
		
}//public class
