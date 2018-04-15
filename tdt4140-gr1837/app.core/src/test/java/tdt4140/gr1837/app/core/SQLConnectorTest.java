package tdt4140.gr1837.app.core;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class SQLConnectorTest {

	@Before
	public void init() {
		UserDatabase.initialize();
	}

	@Test
	public void testGetMuscles() {
		Map<String, Integer> session1;
		try {
			session1 = SQLConnector.getMusclesTrained(1);
			assertTrue(4 == session1.get("biceps"));
		} catch (SQLException e) {
			fail();
		}
	}

	@Test
	public void testCreateGetDeleteSessions() {
		try {
			int sessionCount = SQLConnector.getSessions(5).size();
			int id = SQLConnector.createSession(5, "2017-08-19", "Veldig darlig okt", true);
			assertEquals(sessionCount+1, SQLConnector.getSessions(5).size());
			
			Session session = SQLConnector.getSession(id);
			assertEquals("2017-08-19", session.getDate());
			assertEquals("Veldig darlig okt", session.getNote());
			assertEquals(id, session.getId());
			assertEquals(true, session.isStrength());
			
			SQLConnector.deleteSession(id);
			assertEquals(sessionCount, SQLConnector.getSessions(5).size());
		} catch (SQLException e) {
			fail();
		}
	}

	@Test
	public void testGetUser() throws SQLException {
		try {
			SQLConnector.getUser(-1);
			fail("Hentet bruker med ugyldig ID");
		} catch (IllegalArgumentException e) {
			// Testen passet
		}

		try {
			SQLConnector.getUser(1);
		} catch (IllegalArgumentException e) {
			fail("Fikk ikke hentet bruker med gyldig ID");
		}
	}

	@Test
	public void testGetTrainers() {
		try {
			if (SQLConnector.getTrainers() == null)
				fail();
		} catch (Exception e1) {
			e1.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetStrengthExercises() throws SQLException {
		try {
			if (SQLConnector.getStrengthExercises("Benkpress", 5) == null)
				fail();
		} catch (Exception e1) {
			fail();
		}
	}

	@Test
	public void testCreateGetUpdateDeleteUser() throws SQLException {
		int clientID = SQLConnector.createUser("Kong Harald", "22225555", 81, "Bli den mest veltrente kongen", 1, 5000);
		try {
			SQLConnector.getUser(clientID);
		} catch (IllegalArgumentException e) {
			fail("Fikk ikke hentet nyopprettet bruker");
		}

		SQLConnector.updateUser(clientID, "Kong Harald", "22225555", 81, "Sonja synes jeg er blitt tjukk", 1, 2500);
		assertEquals("Sonja synes jeg er blitt tjukk", SQLConnector.getUser(clientID).getMotivation());
		
		SQLConnector.deleteUser(clientID);
		try {
			SQLConnector.getUser(clientID);
			fail("Fikk hentet bruker som skal vaere slettet");
		} catch (IllegalArgumentException e) {
			// Testen passet
		}
	}

	@Test
	public void testCreateGetUpdateDeleteStrengthExercise() {
		try {
			int index = SQLConnector.getAllExercises(1, true).size();
			int exId = SQLConnector.createStrengthExercise(5, 5, 5, "God okt", 1, 1);
			Exercise exercise = SQLConnector.getAllExercises(1, true).get(index);
			assertTrue(exercise.getNote().equals("God okt"));
			
			SQLConnector.updateStrengthExercise(5, 5, 5, "Darlig okt", 1, exId);
			exercise = SQLConnector.getAllExercises(1, true).get(index);
			assertTrue(exercise.getNote().equals("Darlig okt"));
			
			SQLConnector.deleteStrengthExercise(exId);
			assertEquals(index, SQLConnector.getAllExercises(1, true).size());
		} catch (SQLException e) {
			fail();
		}
	}
	
	@Test
	public void testFeedback() {
		SQLConnector.registerFeedback("testtest", 3);
		assertEquals("testtest", SQLConnector.getFeedback(3));
		
		SQLConnector.registerFeedback("",3);
		assertEquals("", SQLConnector.getFeedback(3));
	}
	
	@Test
	public void testCreateGetDeleteEnduranceExercise() {
		try {
			int index = SQLConnector.getAllExercises(425, false).size();
			int exerciseId = SQLConnector.createEnduranceExercise(200, new Time(200), "Supersjapp", 425, 5);
			Exercise exercise = SQLConnector.getAllExercises(425, false).get(index);
			assertTrue(exercise.getNote().equals("Supersjapp"));
			
			SQLConnector.updateEnduranceExercise(200, new Time(200), "Supertreg", 5, exerciseId);
			exercise = SQLConnector.getAllExercises(425, false).get(index);
			assertTrue(exercise.getNote().equals("Supertreg"));
			
			SQLConnector.deleteEnduranceExercise(exerciseId);
			assertEquals(index, SQLConnector.getAllExercises(425, false).size());
		} catch (SQLException e) {
			fail();
		}
	}
}
