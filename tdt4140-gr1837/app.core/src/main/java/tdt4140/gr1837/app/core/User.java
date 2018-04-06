package tdt4140.gr1837.app.core;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// Midlertidig user-klasse til a fylle userdatabasen, testes derfor ikke
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
			try {
				exercises.addAll(SQLConnector.getAllExercises(session.getId()));
			} catch (SQLException e) {
				e.printStackTrace();
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

	public List<StrengthExercise> getStrengthExercise(String name) {
		try {
			return SQLConnector.getStrengthExercises(name, this.id);
		} catch (SQLException e) {
			return new ArrayList<>();
		}
	}

	/*
	// Gir frekvens per uke for siste maaned, lar den stå i tilfelle frekvensen skal vise det isteden.
	public double getWeeklyTrainingFrequency2() {
		List<Session> sessions = getSessions();
		Calendar oneMonthAgo = Calendar.getInstance();
		oneMonthAgo.add(Calendar.MONTH, -1);
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		List<Session> sessionsLastMonth = sessions.stream()
												  .filter(s -> s.getCalendar().compareTo(oneMonthAgo) >= 0
												  && s.getCalendar().compareTo(today) < 0)
												  .collect(Collectors.toList());
		System.out.println(sessionsLastMonth.toString());
		double sessionsPerMonth = sessionsLastMonth.size();
		double weeksPerMonth = 4.333333;
		DecimalFormat df = new DecimalFormat("#.#");
		return Double.valueOf(df.format(sessionsPerMonth/weeksPerMonth));
	}*/
	
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
		DecimalFormat df = new DecimalFormat("#.#");
		return Double.valueOf(df.format(frequency));
	}
}
