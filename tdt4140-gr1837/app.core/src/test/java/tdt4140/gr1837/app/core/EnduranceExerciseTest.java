package tdt4140.gr1837.app.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Time;

import org.junit.Before;
import org.junit.Test;

public class EnduranceExerciseTest {

	EnduranceExercise exercise = new EnduranceExercise("5,0km løp", "Tuuungt", 5000, new Time(50000));
	
	
	@Test
	public void testExerciseGetters() {
		assertTrue("5,0km løp".equals(exercise.getName()));
		assertTrue("Tuuungt".equals(exercise.getNote()));
		assertTrue(5000 == exercise.getDistance());
	}
	
	@Test
	public void testExerciseSetters() {
		exercise.setDistance(1000);
		exercise.setName("100km");
		exercise.setNote("Ble litt langt");
		Time time = new Time(1000);
		exercise.setTime(time);
		
		assertEquals(new Integer(1000), exercise.getDistance());
		assertEquals("100km", exercise.getName());
		assertEquals("Ble litt langt", exercise.getNote());
		assertEquals(time, exercise.getTime());
	}
	
	
	@Test
	public void testGetAverageSpeed() {
		double averageSpeed = exercise.getAverageSpeed();
		assertTrue(averageSpeed >= 0.0);
	}
	

	@Test
	public void testGetDistanceRepresantation() {
		double distance = exercise.getDistanceRepresantation();
		assertTrue(5.0 == distance);
	}

	@Test
	public void testGetTimeRepresentation() {
		double time = exercise.getDistanceRepresantation();
		assertTrue(5.0 == time);
	}
}
