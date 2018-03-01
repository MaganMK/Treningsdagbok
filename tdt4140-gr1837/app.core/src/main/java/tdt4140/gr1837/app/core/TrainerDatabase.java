package tdt4140.gr1837.app.core;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Midlertidig "database" for a teste at ting funker, testes derfor ikke med junit
public class TrainerDatabase {
	
	private static List<Trainer> trainers = new ArrayList<>();
	private static List<Trainer> offlineTrainerDatabase = new ArrayList<>();

	private static void initializeOfflineDatabase()
	{
		offlineTrainerDatabase.add(new Trainer("Lars", "Box1", "44444444","eksempel@google.no", 1));
		offlineTrainerDatabase.add(new Trainer("Harald", "Box2", "11122111", "aa@aa.a", 2));
		offlineTrainerDatabase.add(new Trainer("Martin", "Box3", "11114111", "Sommerkroppen@2k18.no", 3));
		/*offlineUserDatabase.add(new User("Simon", "11331111", 69, "Øke mobilitet, har ikke lyst til å bli gammel, lizzom", 4));
		offlineUserDatabase.add(new User("Sivert", "11111164", 11, "Get all 'em gurls", 5));
		offlineUserDatabase.add(new User("Sigmund", "44111111", 104, "Kunne gå på skitur med oldebarna", 6));
		offlineUserDatabase.add(new User("Sina", "11111188", 20, "Bikinisesongen", 7));*/
	}
	public static void initialize() {
		if(trainers.isEmpty()) {
			trainers=SQLConnector.getTrainers();
		}
	}
	public static List<Trainer> getOfflineTrainerDatabase()
	{
		if(offlineTrainerDatabase.isEmpty()) {
			initializeOfflineDatabase();
		}
		return offlineTrainerDatabase;
	}

	
	public static Trainer getTrainerById(int id) {
		for (int i = 0; i < trainers.size(); i++) {
			if (id == trainers.get(i).getId()) {
				return trainers.get(i);
			}
		}
		throw new IllegalArgumentException();
	}
	
	public static List<String> getTrainersWithName() {
		return trainers.stream().map(trainer -> trainer.getName()).collect(Collectors.toList());
	}

}