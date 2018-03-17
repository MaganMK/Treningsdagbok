package tdt4140.gr1837.app.core;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;


public class SQLConnectorTest {

	
	@Test
	public void testGetMuscles() {
		Map<String, Integer> session1 = SQLConnector.getMusclesTrained(1);
		assertTrue(4 == session1.get("biceps"));
	}
	
	// Hardkoder data, endres naar insert-metode er implementert
	@Test
	public void testGetSessions() {
		Session testSession = new Session("Bra pump!", "2018-03-01", 2);
		List<Session> sessions = SQLConnector.getSessions(5);
		Session firstSession = sessions.get(0);
		assertEquals(firstSession.getDate(), testSession.getDate());
		assertEquals(firstSession.getNote(), testSession.getNote());
		assertEquals(firstSession.getId(), testSession.getId());
		assertEquals(firstSession.toString(), testSession.toString());
	}
	
	// Hardkoder data, endres naar insert-metode er implementert
	@Test
	public void testGetAllExercises() {
		StrengthExercise testExercise = new StrengthExercise("Benkpress", "Benk hos meg(harvey)", 1, 1, 100);
		List<Exercise> strengthExercises = new ArrayList<>();
		try {
			strengthExercises = SQLConnector.getAllExercises(1);
		} catch (SQLException e1) {
			e1.printStackTrace();
			// fail();
		}
		StrengthExercise lastExercise = null;
		for(Exercise e : strengthExercises) {
			if(e.getNote().equals("Benk hos meg(harvey)")) {
				lastExercise = (StrengthExercise)e;
			}
		}
		assertTrue(lastExercise != null);
		assertEquals(testExercise.getRepetitions(), lastExercise.getRepetitions());
		assertEquals(testExercise.getSet(), lastExercise.getSet());
		assertEquals(testExercise.getWeight(), lastExercise.getWeight());
		assertEquals(testExercise.getName(), lastExercise.getName());
		assertEquals(testExercise.getNote(), lastExercise.getNote());
		assertTrue(strengthExercises.size() > 0);
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
		int clientId = SQLConnector.createUser("Test testesen", "666 66 666", 55, "Fa testene til å kjore", 5);
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
	
}
