package tdt4140.gr1837.app.core;

import javafx.beans.property.SimpleStringProperty;

public abstract class Exercise {
	
	private String name;
	private String note;
	
	public Exercise(String name, String note) {
		this.name = new String(name);
		this.note = note;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
