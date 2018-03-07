package tdt4140.gr1837.app.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StrengthExerciseTest {
	
	StrengthExercise exercise = new StrengthExercise("Knebøy", "Svir i quadriceps", 3, 12, 120);
	
	@Test
	public void testExerciseSetters() {
		exercise.setName("Benkpress");
		exercise.setNote("Brenner i brystvortene");
		assertEquals(exercise.getName(),"Benkpress");
		assertEquals(exercise.getNote(),"Brenner i brystvortene");
	}
	
	@Test
	public void testStrengthExerciseSetters() {
		exercise.setSet(10);
		exercise.setRepetitions(1);
		exercise.setWeight(300);
		assertTrue(10 == exercise.getSet());
		assertTrue(1 == exercise.getRepetitions());
		assertTrue(300 == exercise.getWeight());
		
	}
	
	@Test
	public void testToString() {
		assertEquals("Knebøy", exercise.toString());
	}

}
