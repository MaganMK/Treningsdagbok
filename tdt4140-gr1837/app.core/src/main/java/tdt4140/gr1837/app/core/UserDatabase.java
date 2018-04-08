package tdt4140.gr1837.app.core;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Midlertidig "database" for a teste at ting funker, testes derfor ikke med junit
public class UserDatabase {
	
	private static List<User> users = new ArrayList<>();
	private static List<User> offlineUserDatabase = new ArrayList<>();

	public static void initializeOfflineDatabase() {
		offlineUserDatabase.add(new User("Sindre", "11111111", 23, "Bli svær", 1));
		offlineUserDatabase.add(new User("Harald", "11122111", 21, "Bli fit", 2));
		offlineUserDatabase.add(new User("Stian", "11114111", 20, "Sommerkroppen 2k18", 3));
		offlineUserDatabase.add(new User("Simon", "11331111", 69, "Øke mobilitet, har ikke lyst til å bli gammel, lizzom", 4));
		offlineUserDatabase.add(new User("Sivert", "11111164", 11, "Get all 'em gurls", 5));
		offlineUserDatabase.add(new User("Sigmund", "44111111", 104, "Kunne gå på skitur med oldebarna", 6));
		offlineUserDatabase.add(new User("Sina", "11111188", 20, "Bikinisesongen", 7));
	}
	
	public static void initialize() {
		if(users.isEmpty()) {
			try {
				users = SQLConnector.getUsers();
			} catch (SQLException e) {
				initializeOfflineDatabase();
				users = getOfflineUserDatabase();
			}
		}
	}
	public static List<User> getOfflineUserDatabase() {
		if(offlineUserDatabase.isEmpty()) {
			initializeOfflineDatabase();
		}
		return offlineUserDatabase;
	}
	
	public static List<User> getUsers() {
		return users;
	}
	
	public static User getUser(String name) {
		name = name.toLowerCase();
		for (int i = 0; i < users.size(); i++) {
			String userName = users.get(i).getName().toLowerCase();
			if (name.equals(userName)) {
				return users.get(i);
			}
		}
		for (int i = 0; i < users.size(); i++) {
			String userName = users.get(i).getName().toLowerCase();
			if (userName.contains(name)) {
				return users.get(i);
			}
		}
		return null;
	}
	
	public static User getUserById(int id) {
		for (int i = 0; i < users.size(); i++) {
			if (id == users.get(i).getId()) {
				return users.get(i);
			}
		}
		return new User("Ke", "vin",1, "ke", 1);
	}
	
	public static List<String> getUsersWithName() {
		return users.stream().map(user -> user.getName()).collect(Collectors.toList());
	}

}
