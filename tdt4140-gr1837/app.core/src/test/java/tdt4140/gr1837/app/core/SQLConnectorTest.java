package tdt4140.gr1837.app.core;

import static org.junit.Assert.*;

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
	
}
