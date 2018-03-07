package tdt4140.gr1837.app.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TrainerTest {
		
	Trainer trainer = new Trainer("trener","aasen","113","trener@magan.no",1);
	
	@Test
	public void testGetters() {
		assertEquals("trener", trainer.getName());
		assertEquals("aasen", trainer.getAdress());
		assertEquals("113", trainer.getPhoneNumber());
		assertEquals("trener@magan.no", trainer.getMail());
		assertEquals(1, trainer.getId());
	}
	
	/*
	@Test
	public void testAddClient() {
		trainer.addClient(13);
		assertEquals(1,trainer.getClients().size());
	}*/
	
	
	@Test
	public void testToString() {
		assertEquals(trainer.getName(),trainer.toString());
	}


}
