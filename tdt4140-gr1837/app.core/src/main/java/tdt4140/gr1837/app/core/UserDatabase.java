package tdt4140.gr1837.app.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Midlertidig "database" for å teste at ting funker, testes derfor ikke med junit
public class UserDatabase {
	
	private List<User> users = new ArrayList<>();
	
	public UserDatabase() {
		initialize();
	}
	
	private void initialize() {
		User user1 = new User("Sindre");
		User user2 = new User("Harald");
		User user3 = new User("Stian");
		User user4 = new User("Simon");
		User user5 = new User("Sivert");
		User user6 = new User("Sigmund");
		User user7 = new User("Sina");
		users.add(user1); users.add(user2); users.add(user3); users.add(user4);
		users.add(user5);users.add(user6);users.add(user7);
	}
	
	public User getUser(String name) {
		name = name.toLowerCase();
		for (int i = 0; i < users.size(); i++) {
			String userName = users.get(i).getName().toLowerCase();
			if (name.equals(userName)) {
				return users.get(i);
			}
		}
		return null;
	}
	
	public List<String> getUsersWithName() {
		return this.users.stream().map(user -> user.getName()).collect(Collectors.toList());
	}

}
