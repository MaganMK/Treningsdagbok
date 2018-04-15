package tdt4140.gr1837.app.core;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
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

	// Hardkoder data, endres naar insert-metode er implementert
	@Test
	public void testGetSessions() {
		try {
			Session testSession = new Session("Bra pump!", "2018-03-01", 2);
			List<Session> sessions = SQLConnector.getSessions(5);
			Session session = null;
			for (Session s : sessions) {
				if (s.getDate().equals("2018-03-01")) {
					session = s;
				}
			}
			assertEquals(session.getDate(), testSession.getDate());
			assertEquals(session.getNote(), testSession.getNote());
			assertEquals(session.getId(), testSession.getId());
			assertEquals(session.toString(), testSession.toString());
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
	public void testCreateGetDeleteUser() throws SQLException {
		int clientId = SQLConnector.createUser("Test testesen", "666 66 666", 55, "Fa testene til å kjore", 5, 250);
		try {
			SQLConnector.getUser(clientId);
		} catch (IllegalArgumentException e) {
			fail();
		}

		SQLConnector.deleteUser(clientId);
		try {
			SQLConnector.getUser(clientId);
			fail();
		} catch (IllegalArgumentException e) {
			// Testen passet
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
	public void testCreateUpdateUser() throws SQLException {
		int clientID = SQLConnector.createUser("Kong Harald", "22225555", 81, "Bli den mest veltrente kongen", 1, 5000);
		try {
			SQLConnector.getUser(clientID);
		} catch (IllegalArgumentException e) {
			fail();
		}

		SQLConnector.updateUser(clientID, "Kong Harald", "22225555", 81, "Sonja synes jeg er blitt tjukk", 1, 2500);
		if (!SQLConnector.getUser(clientID).getMotivation().equals("Sonja synes jeg er blitt tjukk")) {
			fail();
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
