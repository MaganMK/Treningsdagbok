package tdt4140.gr1837.app.core;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TrainerDatabaseTest {
	
	@Before
	public void init() {
		UserDatabase.initialize();
		TrainerDatabase.initialize();
	}
	
	@Test
	public void testOfflineDatabase() {
		Assert.assertEquals(TrainerDatabase.getOfflineTrainerDatabase().size(),3);
	}
	
	@Test
	public void testGetTrainerById() {
		Assert.assertEquals(TrainerDatabase.getTrainerById(1).getName(), "Barclay Graves");
	}
	
	@Test
	public void testGetTrainersByName() {
		Assert.assertEquals(TrainerDatabase.getTrainersWithName().get(0), "Barclay Graves");
	}
	

}
