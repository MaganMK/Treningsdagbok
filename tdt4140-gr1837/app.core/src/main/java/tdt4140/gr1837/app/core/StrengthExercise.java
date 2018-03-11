package tdt4140.gr1837.app.core;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class StrengthExercise extends Exercise {
	
	private Integer set;
	private Integer repetitions;
	private Integer weight;
	private Integer session_id;
	
	public StrengthExercise(String name, 
							String note,
							int set, 
							int repetitions, 
							int weight) {
		super(name, note);
		this.set = new Integer(set);
		this.repetitions = new Integer(repetitions);
		this.weight = new Integer(weight);
		
	}
	
	public void setSessionId(int session_id) {
		this.session_id = new Integer(session_id);
	}
	public Integer getSessionId() {
		return this.session_id;
	}

	public Integer getSet() {
		return set;
	}

	public void setSet(Integer set) {
		this.set = set;
	}

	public Integer getRepetitions() {
		return repetitions;
	}

	public void setRepetitions(Integer repetitions) {
		this.repetitions = repetitions;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	// Kalkulerer 1RM for en ovelse
	public double getOneRepMax(){
		return repetitions == 1 ? weight : weight * (1+(repetitions/Double.valueOf(30)));
	}
	
	// Kalkulerer vektvolum for en ovelse
	public double getWeightVolume() {
		return set*weight*repetitions;
	}
	
}







