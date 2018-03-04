package tdt4140.gr1837.app.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLConnector {
	private static String url = "jdbc:mysql://mysql.stud.ntnu.no/didris_test1";
	private static String username = "didris_test";
	private static String password = "1234";
	private static Connection connection;
	
	// Metode for aa hente en SQL connection
	public static Connection getConnection() throws SQLException {
		if(connection == null) {
			try {
				System.out.println("Connecting database...");
				connection = DriverManager.getConnection(url, username, password);
				System.out.println("Successfully connected to the database");
			}
			catch(SQLException e) {
				System.out.println("Could not connect to database");
				throw e;
			}
		}
		return connection;
	}
	
	// Metode for aa lukke en SQL connection
	public static void closeConnection() {
		if(connection != null) {
			try {
				connection.close();
				System.out.println("Connection closed");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Metode for aa hente ut resultatet fra en SQL spoerring
	public static ResultSet getResultSet(String query) throws SQLException {
		Connection connection;
		try {
			connection = SQLConnector.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch(SQLException e) {
			throw e;
		}
	}
	
	// Metode for aa hente klientene
	public static List<User> getUsers() {
		try {
			ResultSet rs = getResultSet("SELECT * FROM Client");
			List<User> users = new ArrayList<>();
			while(rs.next()) {
				User user = new User(rs.getString("name"), 
									rs.getString("phone"), 
									rs.getInt("age"), 
									rs.getString("motivation"), 
									rs.getInt("client_id")
				);
				users.add(user);
			}
			return users;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return UserDatabase.getOfflineUserDatabase();
		}
	}
	
	// Metode for aa hente ovelser til spesifikk okt
	public static List<Exercise> getAllExercises(int sessionId) {
		List<Exercise> exercises = new ArrayList<>();
		exercises.addAll(getStrengthExercises(sessionId));
		exercises.addAll(getEnduranceExercises(sessionId));
		return exercises;
	}
	
	// Metode for aa hente styrkeovelser til spesifikk okt
	private static List<Exercise> getStrengthExercises(int sessionId) {
		try {
			List<Exercise> strengthExercises = new ArrayList<>();
			ResultSet rs = getResultSet("SELECT * FROM Strength_Session NATURAL JOIN Exercise WHERE session_id="+sessionId);
			while(rs.next()) {
				Exercise strengthExercise = new StrengthExercise(rs.getString("session_name"),
																 rs.getString("note"),
																 rs.getInt("set"),
																 rs.getInt("reps"),
																 rs.getInt("weight")
				);
				strengthExercises.add(strengthExercise);
			}
			return strengthExercises;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return new ArrayList<Exercise>();
		}
	}
	
	// Metode for aa hente utholdenhetsovelser, implementeres i senere sprint
	private static List<Exercise> getEnduranceExercises(int sessionId) {
		return new ArrayList<Exercise>();
	}
	
	
	// Metode for aa hente oktene
	public static List<Session> getSessions(int id) {
		try {
			List<Session> sessions = new ArrayList<>();
			ResultSet rs = getResultSet("SELECT * FROM Session WHERE client_id="+id);
			while(rs.next()) {
				Session session = new Session(rs.getString("note"), 
									rs.getString("date"), 
									rs.getInt("session_id")
				);
				sessions.add(session);
			}
			return sessions;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return new ArrayList<Session>();
		}
	}
	
	public static Map<String, Integer> getMusclesTrained(int sessionID){
		try {
			
			Map<String, Integer> musclesTrained = new HashMap<>();
			ResultSet rs1 = getResultSet("SELECT * FROM Strength_Session WHERE session_id="+sessionID);
			while(rs1.next()){
					ResultSet rs2 = getResultSet("SELECT * FROM Muscle_Trained WHERE exercise_id="+ rs1.getInt("exercise_id"));	
					while(rs2.next()){
						ResultSet rs3 = getResultSet("SELECT * FROM Muscle WHERE muscle_id="+ rs2.getInt("muscle_id"));	
						while(rs3.next()){
							musclesTrained.put(rs3.getString("muscle_name"), rs2.getInt("degree"));
						}
				}

			}
			return musclesTrained;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return new HashMap<String, Integer>();
		}
	}

	// Metode for aa hente trenere
	public static List<Trainer> getTrainers() {
		try {
			List<Trainer> trainers = new ArrayList<>();
			ResultSet rs = getResultSet("SELECT * FROM Trainer");
			while(rs.next()) {
				Trainer trainer = new Trainer(rs.getString("name"), 
									rs.getString("adress"), 
									rs.getString("phone"),
									rs.getString("mail"),
									rs.getInt("trainer_id")
				);
				trainers.add(trainer);
				ResultSet clientsRS = getResultSet("SELECT * FROM Personal_Trainer WHERE trainer_id="+trainer.getId());
				while(clientsRS.next()){
					trainer.addClient(clientsRS.getInt("client_id"));
				}
			}
			return trainers;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return new ArrayList<Trainer>();
		}
	}
}

