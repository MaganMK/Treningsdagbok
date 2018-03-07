package tdt4140.gr1837.app.core;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class UserDatabaseTest {
	
	@Before
	public void init() {
		UserDatabase.initialize();
		TrainerDatabase.initialize();
	}
	
	@Test
	public void testGetUser() {
		User user1 = UserDatabase.getUser("Ingen med dette navnet");
		User user2 = UserDatabase.getUser("snow");
		assertTrue(user1 == null);
		assertTrue(user2 != null);
	}
	
	@Test
	public void testGetters() {
		assertTrue(UserDatabase.getUsers().size() > 0);
		assertTrue(UserDatabase.getUsersWithName().contains("Hyacinth Harvey"));
	}

}
