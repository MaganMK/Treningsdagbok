package tdt4140.gr1837.app.core;

import java.util.Calendar;

import org.junit.Test;

import junit.framework.TestCase;

public class SessionTest extends TestCase {
	
	Session firstSession = new Session("Bra okt", "2018-04-01", 1000);
	Session lastSession = new Session("Fikk testa meg skikkelig", "2018-04-02", 1001);
	
	@Test
	public void testCompareTo() {
		assertTrue(firstSession.compareTo(lastSession) < 0);
	}
	
	@Test
	public void testDates() {
		assertTrue(firstSession.getDay() == 1);
		assertTrue(firstSession.getMonth() == 4);
		assertTrue(firstSession.getYear() == 2018);
		Calendar cal = firstSession.getCalendar();
		assertTrue(cal.getWeekYear() == 2018);
	}

	

}
