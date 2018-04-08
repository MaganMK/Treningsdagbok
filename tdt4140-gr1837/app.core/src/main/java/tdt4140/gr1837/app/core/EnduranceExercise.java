package tdt4140.gr1837.app.core;

import java.sql.Time;

public class EnduranceExercise extends Exercise {
	
	private Integer distance, sessionId;
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
	
	public void setSessionId(int id) {
		this.sessionId = id;
	}
	
	public int getSessionId(){
		return sessionId;
	}

	public double getAverageSpeed() {
		double hours = Double.valueOf(time.toString().substring(0, 2)) + Double.valueOf(time.toString().substring(3, 5))/60 + Double.valueOf(time.toString().substring(6, 8))/3600;
			
		return distance/(hours*1000);
	}
	public double getDistanceRepresantation() {
		return Double.valueOf(distance/1000);
	}
	
	public double getTimeRepresentation() {
		return Double.valueOf(time.toString().substring(0, 2))*60 + Double.valueOf(time.toString().substring(3, 5)) + Double.valueOf(time.toString().substring(6, 8))/60;
		
	}

}

