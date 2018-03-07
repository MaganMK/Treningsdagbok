package tdt4140.gr1837.app.core;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class UserTest {
	
	User user = new User("Mons","112",9,"damer",2);
	Trainer trainer = new Trainer("Jarle","Stien","99999","Jarle@Fit.no",1);
			
	@Before
	public void init() {
		user.setTrainer(trainer);
	}
	
	@Test
	public void testGetters() {
		assertEquals("Mons", user.getName());
		assertEquals("112", user.getPhoneNumber());
		assertEquals(9, user.getAge());
		assertEquals("damer", user.getMotivation());
		assertEquals(2, user.getId());
		assertEquals(user.getTrainer().getName(), "Jarle");
	}
	
	@Test
	public void testToString() {
		assertEquals("Mons",user.toString());
	}
	

}
