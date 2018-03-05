package tdt4140.gr1837.app.core;

public class Session {
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
	
	@Override
	public String toString() {
		String reversedDate = "";
		reversedDate = date.substring(8) + '/' + date.substring(5,7) + "-" + date.substring(0,4);
		return reversedDate;
	}
}
