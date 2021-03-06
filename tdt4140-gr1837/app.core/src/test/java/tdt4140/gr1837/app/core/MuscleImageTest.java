package tdt4140.gr1837.app.core;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class MuscleImageTest {
	
	@Test
	public void testUpdateMuscles() {
		List<User> users;
		try {
			users = SQLConnector.getUsers();
			MuscleImage tester = new MuscleImage(users.get(24));
			
			assertTrue(tester.getMusclesWithPrecentages().get("biceps") == 0.0);
			
			tester.updateMuscle("lats", 4);
			tester.increaseTotalValue(4);
			tester.updateMuscle("biceps", 4);
			tester.increaseTotalValue(4);
			
			assertTrue(tester.getMusclesWithPrecentages().get("biceps") == 0.5);
			assertTrue(tester.getMusclesWithPrecentages().get("lats") == 0.5);
			assertTrue(tester.getMusclesWithPrecentages().get("bryst") == 0.0);
		} catch (SQLException e) {
			fail();
		}
	}
}
