package hk.edu.uic.doordie.server.test;

import hk.edu.uic.doordie.server.model.dbc.DatabaseConnection;

public class TestDatabaseConnection {
	public static void main(String[] args) throws Exception {
		DatabaseConnection dbc = new DatabaseConnection();
		if (dbc.getConnection() != null) {
			System.out.println("Connect Successfully!");
			dbc.close();
		} else {
			System.out.println("Connect fail!");
		}
		
	}
}
