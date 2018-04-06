package tdt4140.gr1837.app.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Session implements Comparable<Session> {
	private String note;
	private int id;
	private String date;
	
	public Session(String note, String date, int id) {
		this.note = note;
		this.date = date;
		this.id = id;
	}
	
	public String getNote() {
		return note;
	}
	public int getId() {
		return id;
	}
	
	public String getDate() {
		return date;
	}
	
	public int getYear() {
		return Integer.valueOf(date.substring(0,4));
	}
	
	public int getMonth() {
		return Integer.valueOf(date.substring(5,7));
	}
	
	public int getDay() {
		return Integer.valueOf(date.substring(8));
	}
	
	@Override
	public String toString() {
		return date.substring(8) + '/' + date.substring(5,7) + "-" + date.substring(0,4);
	}
	
	public Calendar getCalendar() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-mm", Locale.GERMAN);
		try {
			cal.setTime(sdf.parse(getDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal;
	}

	@Override
	public int compareTo(Session other) {
		return this.getCalendar().compareTo(other.getCalendar());
	}
}
