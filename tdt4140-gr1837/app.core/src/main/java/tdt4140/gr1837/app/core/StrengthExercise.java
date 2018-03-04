package tdt4140.gr1837.app.core;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class StrengthExercise extends Exercise {
	
	private Integer set;
	private Integer repetitions;
	private Integer weight;
	
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

}
