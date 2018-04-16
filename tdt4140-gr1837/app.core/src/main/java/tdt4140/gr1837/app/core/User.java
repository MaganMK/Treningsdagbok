package tdt4140.gr1837.app.core;

import java.util.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class User {

	private String name;
	private String phoneNumber;
	private int age;
	private String motivation;
	private int id;
	private Trainer trainer;

	public User(String name, String phoneNumber, int age, String motivation, int id) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.age = age;
		this.motivation = motivation;
		this.id = id;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return this.name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public int getAge() {
		return age;
	}

	public String getMotivation() {
		return motivation;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	@Override
	public String toString() {
		return getName();
	}

	// Vi trenger:
	// - Navn pa ovelse
	// - Vekt
	// - Representasjon 1rm/vektvolum

	// Returnerer alle exercises en bruker har hatt ila alle sessions
	public List<Exercise> getExercises() {
		List<Exercise> exercises = new ArrayList<>();
		List<Session> sessions;
		try {
			sessions = SQLConnector.getSessions(this.getId());
		} catch (SQLException e1) {
			sessions = new ArrayList<Session>();
		}
		for (Session session : sessions) {
			if(session.isStrength()){
			try {
				exercises.addAll(SQLConnector.getAllExercises(session.getId(), session.isStrength()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		}
		return exercises;
	}


	public List<Session> getSessions() {
		try {
			return SQLConnector.getSessions(this.getId());
		} catch (SQLException e) {
			return new ArrayList<>();
		}
	}
	
	public double getProgress() {
		return 0.5;
	}
	
	// Faar distansemaalet
	public int getDistanceToRun() throws SQLException {
		int distance = SQLConnector.getDistanceToRun(this.id);
		if(distance <= 0) {
			return 1;
		}
		return distance;
	}
	
	// Faar distansen lopt siste maanden
	public int getDistanceRun() throws SQLException { 
		ZoneId z = ZoneId.of("America/Montreal");
		LocalDate ld = LocalDate.now( z ); 
		ld = ld.minusMonths(1);
		System.out.println(String.format(ld.toString()));
		return SQLConnector.getDistanceRun(this.id, ld.toString());
	}
	
	public List<StrengthExercise> getStrengthExercise(String name) {
		try {
			return SQLConnector.getStrengthExercises(name, this.id);
		} catch (SQLException e) {
			return new ArrayList<>();
		}
	}
	
	// Gaar igjennom alle oktene til en bruker og finner ukesnittet fom forste okt tom i dag
	public double getWeeklyTrainingFrequency() {
		List<Session> sessions = getSessions();
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		Collections.sort(sessions);

		if (sessions.size() == 0) {
			return 0;
		}
		Session firstSession = sessions.get(0);
		Calendar firstTraining = Calendar.getInstance();
		firstTraining.set(firstSession.getYear(), firstSession.getMonth()-1, firstSession.getDay());
		long diff = today.getTimeInMillis() - firstTraining.getTimeInMillis();
		double weeksBetween = diff / (7*24 * 60 * 60 * 1000);
		if (weeksBetween == 0.0) {
			weeksBetween = 1;
		}
		double frequency = sessions.size() / weeksBetween;
		DecimalFormat df = new DecimalFormat("#.0");
		String formatedFrequency = df.format(frequency);
		String result = "";
		for(char c : formatedFrequency.toCharArray())
		{
			if(c == ',')
			{
				c = '.';
			}
			result += c;
		}
		return Double.valueOf(result);
	}
	
	public List<String> getMostUsedExercises(){
		List<String> mostUsed = SQLConnector.mostUsedExercise(id);
		if(mostUsed.size() >= 3) {
			mostUsed = mostUsed.subList(0, 3);
		}
		else{
			mostUsed = mostUsed.subList(0, mostUsed.size());
		}
		return mostUsed;
	}
	

	public List<EnduranceExercise> getEnduranceExercise(String name){
		try {
			return SQLConnector.getEnduranceExercises(name, this.id);
		} catch (SQLException e) {
			return new ArrayList<>();
		}	
	}
	
	public List<EnduranceExercise> getEnduranceExercises() {
		List<EnduranceExercise> endurance = new ArrayList<>();
		List<Session> sessions;
		try {
			sessions = SQLConnector.getSessions(this.getId());
		} catch (SQLException e)
		{
			sessions = new ArrayList<Session>();
		}
		
		for (Session session : sessions) {
			if(!session.isStrength()){
				try {
					endurance.addAll(SQLConnector.getEnduranceExercises(session.getId()));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
		}
		return endurance;
	}

}
