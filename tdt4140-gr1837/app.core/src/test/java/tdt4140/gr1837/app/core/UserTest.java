package tdt4140.gr1837.app.core;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class UserTest {
	
	User user = new User("Mons","112",9,"damer",2);
	Trainer trainer = new Trainer("Jarle","Stien","99999","Jarle@Fit.no",1);
			
	@Before
	public void init() {
		user.setTrainer(trainer);
	}
	
	@Test
	public void testGetters() {
		Assert.assertEquals("Mons", user.getName());
		Assert.assertEquals("112", user.getPhoneNumber());
		Assert.assertEquals(9, user.getAge());
		Assert.assertEquals("damer", user.getMotivation());
		Assert.assertEquals(2, user.getId());
		Assert.assertEquals(user.getTrainer().getName(), "Jarle");
	}
	

}
