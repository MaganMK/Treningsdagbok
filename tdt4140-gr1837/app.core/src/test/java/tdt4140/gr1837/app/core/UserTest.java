package tdt4140.gr1837.app.core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



public class UserTest {
	
	User user = new User("Mons","112",9,"damer",2);
	Trainer trainer = new Trainer("Jarle","Stien","99999","Jarle@Fit.no",1);
			
	@Before
	public void init() {
		user.setTrainer(trainer);
		UserDatabase.initialize();
		TrainerDatabase.initialize();
	}
	
	@Test
	public void testGetters() {
		assertEquals("Mons", user.getName());
		assertEquals("112", user.getPhoneNumber());
		assertEquals(9, user.getAge());
		assertEquals("damer", user.getMotivation());
		assertEquals(2, user.getId());
		assertEquals(user.getTrainer().getName(), "Jarle");
	}
	
	@Test
	public void testToString() {
		assertEquals("Mons",user.toString());
	}
	
	@Test
	public void testGetExercises() {
		User user = UserDatabase.getUser("Test User");
		assertTrue(user.getExercises().size() > 0);
	}
	
	@Test
	public void testGetSessions() {
		User user = UserDatabase.getUser("Test User");
		assertTrue(user.getSessions().size() > 0);
	}
	
	@Test
	public void testGetStrengthExercise() {
		User user = UserDatabase.getUser("Test User");
		assertTrue(user.getStrengthExercise("Benkpress").size() > 0);
	}
	
	@Test
	public void testGetWeeklyTrainingFrequency() {
		User user = UserDatabase.getUser("Test User");
		assertTrue(user.getWeeklyTrainingFrequency() >= 0);
	}
	
	@Test
	public void testGetMosedUsedExercises() {
		User user = UserDatabase.getUser("Hyacinth Harvey");
		assertTrue(user.getMostUsedExercises().size() >= 0);
	}
	

}
