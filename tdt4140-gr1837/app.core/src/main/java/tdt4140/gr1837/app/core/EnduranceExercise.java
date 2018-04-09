package tdt4140.gr1837.app.core;

import java.sql.Time;

public class EnduranceExercise extends Exercise {
	
	private Integer distance;
	private Time time;

	public EnduranceExercise(String name, String note, int distance, Time time) {
		super(name, note);
		this.distance = new Integer(distance);
		this.time = time;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}
}
