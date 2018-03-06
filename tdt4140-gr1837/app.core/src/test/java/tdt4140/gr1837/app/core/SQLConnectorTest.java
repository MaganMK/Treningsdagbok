package tdt4140.gr1837.app.core;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;


public class SQLConnectorTest {

	
	@Test
	public void testGetMuscles() {
		Map<String, Integer> session1 = SQLConnector.getMusclesTrained(1);
		
		for (String muscle : session1.keySet()){
			assertEquals("biceps", muscle);
			assertTrue(4 == session1.get(muscle));
		}
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
	
	@Test
	public void testGetAllExercises() {
		StrengthExercise testExercise = new StrengthExercise("shrugs", "JEG ELSKER TRAPS!", 4, 12, 50);
		List<Exercise> strengthExercises = SQLConnector.getAllExercises(5);
		StrengthExercise lastExercise = (StrengthExercise)strengthExercises.get(strengthExercises.size() - 1);
		assertEquals(testExercise.getRepetitions(), lastExercise.getRepetitions());
		assertEquals(testExercise.getSet(), lastExercise.getSet());
		assertEquals(testExercise.getWeight(), lastExercise.getWeight());
		assertEquals(testExercise.getName(), lastExercise.getName());
		assertEquals(testExercise.getNote(), lastExercise.getNote());
	}
	
	@Test
	public void testTableChanges() {
		
	}
	
}
