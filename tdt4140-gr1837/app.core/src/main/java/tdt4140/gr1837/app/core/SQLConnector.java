package tdt4140.gr1837.app.core;

import java.io.IOException;
import java.sql.Connection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

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
	
	// exercise_id i konstruktoren er fremmednokkel til ovelse-tabell som sier hvilken ovelse det er.
	public static void createStrengtExercise(int reps, int sett, int weight, String note, int session_id, int exercise_id) throws SQLException {
		Connection conn = SQLConnector.getConnection();
		Statement statement = conn.createStatement();
		int exerciseId = getMaximumIdFromDBPlusOneAlsoKnownAsNextID(); 	// Attribut som i tabellen / databasen heter strength_exercise_id
		statement.executeUpdate(String.format
				("INSERT INTO Strength_Exercise VALUES(%d, %d, %d, '%s', %d, %d, %d)", reps, sett, weight, note, session_id, exercise_id, exerciseId));
	}
		
	public static void updateStrengthExercise(int reps, int sett, int weight, String note, int session_id, int exercise_id, int strength_exercise_id) 
			throws SQLException {
		Connection conn = SQLConnector.getConnection();
		Statement statement = conn.createStatement();
		statement.executeUpdate(String.format
				("UPDATE Strength_Exercise SET reps=%d, sett=%d, weight=%d, note='%s', session_id=%d, exercise_id=%d, strength_exercise_id=%d",
						reps, sett, weight, note, session_id, exercise_id, strength_exercise_id));

	// Metode for aa hente klientene
	public static User getUser(int clientId) throws SQLException, IllegalArgumentException {
			ResultSet rs = getResultSet("SELECT * FROM Client WHERE client_id=" + clientId);
			while(rs.next()) {
				return new User(rs.getString("name"), 
									rs.getString("phone"), 
									rs.getInt("age"), 
									rs.getString("motivation"), 
									rs.getInt("client_id")
				);
			}
			throw new IllegalArgumentException("Klient med denne id-en finnes ikke");
	}
	
	// Metode for aa hente ovelser til spesifikk okt
	public static List<Exercise> getAllExercises(int sessionId) throws SQLException{
		List<Exercise> exercises = new ArrayList<>();
		exercises.addAll(getStrengthExercises(sessionId));
		exercises.addAll(getEnduranceExercises(sessionId));
		return exercises;
	}
	
	// Metode for aa hente styrkeovelser til spesifikk okt
	private static List<Exercise> getStrengthExercises(int sessionId) throws SQLException {
		List<Exercise> strengthExercises = new ArrayList<>();
		ResultSet rs = getResultSet("SELECT * FROM Strength_Session NATURAL JOIN Exercise WHERE session_id="+sessionId);
		while(rs.next()) {
			Exercise strengthExercise = new StrengthExercise(rs.getString("exercise_name"),
															 rs.getString("note"),
															 rs.getInt("sett"),
															 rs.getInt("reps"),
															 rs.getInt("weight")
			);
			strengthExercises.add(strengthExercise);
		}
		return strengthExercises;
	}
	
	public static List<StrengthExercise> getStrengthExercises(String exerciseName, int userID) {
		try {
			List<StrengthExercise> strengthExercises = new ArrayList<>();
			ResultSet rs = getResultSet("SELECT * FROM `Strength_Exercise` NATURAL JOIN `Exercise` INNER JOIN `Session` ON (`Session`.`session_id` = Strength_Exercise.session_id) WHERE exercise_name =" + "\"" +exerciseName+ "\"" + "AND client_id=" + userID);
			while(rs.next()) {
				StrengthExercise strengthExercise = new StrengthExercise(rs.getString("exercise_name"),
																 rs.getString("note"),
																 rs.getInt("sett"),
																 rs.getInt("reps"),
																 rs.getInt("weight")
				);
				strengthExercise.setSessionId(rs.getInt("session_id"));
				strengthExercises.add(strengthExercise);
				
			}
			return strengthExercises;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return new ArrayList<StrengthExercise>();
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
	
	// Metode for aa hente oktene
	public static Session getSession(int id) throws SQLException, IllegalArgumentException {

		ResultSet rs = getResultSet("SELECT * FROM Session WHERE session_id="+id);
		while(rs.next()) {
			return new Session(rs.getString("note"), 
								rs.getString("date"), 
								rs.getInt("session_id"));
		}
		throw new IllegalArgumentException("Okt med denne id-en finnes ikke");
	}
	
	// Metode for aa opprette en okt
	public static int createSession(int clientId, String date, String note) throws SQLException {
		Connection conn = SQLConnector.getConnection();
		Statement statement = conn.createStatement();
		int sessionId = getMaximumIdFromDBPlusOneAlsoKnownAsNextID("session_id", "Session");
		statement.executeUpdate(String.format("INSERT INTO Session VALUES(%d, '%s', '%s', %d)", clientId, date, note, sessionId));
		return sessionId;
	}
	
	public static Map<String, Integer> getMusclesTrained(int sessionID){
		try {
			
			Map<String, Integer> musclesTrained = new HashMap<>();
			ResultSet rs1 = getResultSet("SELECT * FROM Strength_Exercise WHERE session_id="+sessionID);
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

	public static Session getSessionByExercise(Integer sessionId) {
		try {
			ResultSet rs = getResultSet("SELECT * FROM Session WHERE session_id="+sessionId);
			Session session = null;
			while(rs.next()) {
				session = new Session(rs.getString("note"), 
									rs.getString("date"), 
									rs.getInt("session_id")
				);
			}
			return session;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}
	}
	
	// Metode for aa opprette en bruker
	public static int createUser(String name, String phoneNumber, int age, String motivation, int trainerId) throws SQLException {
		Connection conn = SQLConnector.getConnection();
		Statement statement = conn.createStatement();
		int clientId = getMaximumIdFromDBPlusOneAlsoKnownAsNextID("client_id", "Client");
		statement.executeUpdate(String.format("INSERT INTO Client (name, phone, age, motivation, client_id, trainer_id) "
											+ "VALUES('%s', '%s', %d, '%s', %d, %d)", name, phoneNumber, age, motivation, clientId, trainerId));
		statement = conn.createStatement();
		statement.executeUpdate(String.format("INSERT INTO Personal_Trainer(client_id, trainer_id) VALUES(%d, %d)", clientId, trainerId));
		return clientId;
	}
	
	private static int getMaximumSessionIdFromDBPlusOneAlsoKnownAsNextID() throws SQLException {
		ResultSet rs = getResultSet("SELECT MAX(session_id) AS maximum FROM Session");
		if (rs.next()) {
			return rs.getInt("maximum") + 1;
		} return 1;

	// Metode for aa slette en bruker
	public static void deleteUser(int clientId) throws SQLException {
		Connection conn = SQLConnector.getConnection();
		Statement statement = conn.createStatement();
		statement.executeUpdate("DELETE FROM Client WHERE client_id=" + clientId);
	}
	
	// Metode for aa endre en bruker
	public static void updateUser(int clientId, String name, String phoneNumber, int age, String motivation, int trainerId) throws SQLException {
		Connection conn = SQLConnector.getConnection();
		Statement statement = conn.createStatement();
		statement.executeUpdate(String.format("UPDATE Client SET name='%s', phone='%s', age=%d, motivation='%s', trainer_id=%d ", 
												name, phoneNumber, age, motivation, trainerId)
											+ "WHERE client_id=" + clientId);
	}
	
	private static int getMaximumIdFromDBPlusOneAlsoKnownAsNextID(String param_id, String param) throws SQLException {
		ResultSet rs = getResultSet("SELECT MAX("+ param_id + ") AS maximum FROM " + param);
		if (rs.next()) {
			return rs.getInt("maximum") + 1;
		}
		return 1;
	}
	
	public static void createExercise(String name, int id) throws SQLException {
		Connection conn = SQLConnector.getConnection();
		Statement statement = conn.createStatement();
		
	}
	
	public static void main(String[] args) throws SQLException, ClientProtocolException, IOException
	{
		String       postUrl       = "http://localhost:8000/session";// put in your url
		Gson         gson          = new Gson();
		HttpClient   httpClient    = HttpClientBuilder.create().build();
		HttpPost     post          = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity("clientID=4&date=2017-10-10&note=me", "utf-8");//gson.tojson() converts your pojo to json
		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse  response = httpClient.execute(post);
		System.out.println("breakpoint");
		System.out.println(EntityUtils.toString(response.getEntity()));
		System.out.println("breakpoint2");
	}
}

