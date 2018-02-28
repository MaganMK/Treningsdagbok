package tdt4140.gr1837.app.core;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Midlertidig "database" for a teste at ting funker, testes derfor ikke med junit
public class UserDatabase {
	
	private List<User> users = new ArrayList<>();
	private static List<User> offlineUserDatabase = new ArrayList<>();
	public UserDatabase() {
		initialize();
	}
	private static void initializeOfflineDatabase()
	{
		offlineUserDatabase.add(new User("Sindre", "11111111", 23, "Bli svær", 1));
		offlineUserDatabase.add(new User("Harald", "11122111", 21, "Bli fit", 2));
		offlineUserDatabase.add(new User("Stian", "11114111", 20, "Sommerkroppen 2k18", 3));
		offlineUserDatabase.add(new User("Simon", "11331111", 69, "Øke mobilitet, har ikke lyst til å bli gammel, lizzom", 4));
		offlineUserDatabase.add(new User("Sivert", "11111164", 11, "Get all 'em gurls", 5));
		offlineUserDatabase.add(new User("Sigmund", "44111111", 104, "Kunne gå på skitur med oldebarna", 6));
		offlineUserDatabase.add(new User("Sina", "11111188", 20, "Bikinisesongen", 7));
	}
	private void initialize() {
		/*User user1 = new User("Sindre");
		User user2 = new User("Harald");
		User user3 = new User("Stian");
		User user4 = new User("Simon");
		User user5 = new User("Sivert");
		User user6 = new User("Sigmund");
		User user7 = new User("Sina");
		users.add(user1); users.add(user2); users.add(user3); users.add(user4);
		users.add(user5);users.add(user6);users.add(user7);users.add(user8);*/
		users=SQLConnector.getUsers();
	}
	public static List<User> getOfflineUserDatabase()
	{
		if(offlineUserDatabase.isEmpty()) {
			initializeOfflineDatabase();
		}
		return offlineUserDatabase;
	}
	public User getUser(String name) {
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
	
	public List<String> getUsersWithName() {
		return this.users.stream().map(user -> user.getName()).collect(Collectors.toList());
	}

}
