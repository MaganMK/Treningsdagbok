package tdt4140.gr1837.app.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnector {
	private static String url = "jdbc:mysql://mysql.stud.ntnu.no/didris_test1";
	private static String username = "didris_test";
	private static String password = "1234";
	private static Connection connection;
	public static Connection getConnection(){
		if(connection==null){
			try {
				System.out.println("Connecting database...");
				connection = DriverManager.getConnection(url, username, password);
				System.out.println("Successfully connected to the database");
			}
			catch(SQLException e) {
				System.out.println("Could not connect to database");
				return null;
			}
		}
		return connection;
	}
	public static void closeConnection() {
		if(connection!=null) {
			try {
				connection.close();
				System.out.println("Connection closed");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String args[]) throws SQLException {
		Connection connection = SQLConnector.getConnection();
		insert_client(connection, "\"Per Pålsen\"", 41010101, 20, "\"msakmdmkm\"");
	}
	private static boolean insert_client(Connection connection, String name, int mobile_number, int age, String motivation) throws SQLException
	{
		String q = String.format("INSERT INTO Klient(navn, tlf, alder, motivasjon,K_ID) VALUES(%s,%s,%s,%s,4);", name, mobile_number, age, motivation);
		Statement stmt = connection.createStatement();
		stmt.executeUpdate(q);
		return true;
	}
}
