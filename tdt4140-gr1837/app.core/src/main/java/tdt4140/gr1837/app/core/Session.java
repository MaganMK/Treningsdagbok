package tdt4140.gr1837.app.core;

public class Session {
	private String note;
	private int id;
	private String date;
	
	public Session(String note, String date, int id) {
		this.note=note;
		this.date=date;
		this.id=id;
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
		return id + " " + date;
	}
}
