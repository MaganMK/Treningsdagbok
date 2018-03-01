package tdt4140.gr1837.app.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Klasse for aa haandtere "muskelmannen"
public class MuscleImage {

	/*
	 *  Ved aa bruke hashmap kan man refferere til riktig muskel ved navn.
	 *  Slik at det blir lettere aa oppdatere med navn fra databasen.
	 */
	private Map<String, Integer> muscles = new HashMap<>();
	private User user;
	
	// Totalt antall verdier som er gitt, slik at vi kan regne prosent
	private int totalValue = 0;
	

	public MuscleImage(User user){
		this.user = user;
		muscles.put("underarmer", 0);
		muscles.put("abs", 0);
		muscles.put("triceps", 0);
		muscles.put("teres", 0);
		muscles.put("postDelts", 0);
		muscles.put("obliques", 0);
		muscles.put("lats", 0);
		muscles.put("frontDelts", 0);
		muscles.put("erector", 0);
		muscles.put("calves", 0);
		muscles.put("biceps", 0);
		muscles.put("quads", 0);
		muscles.put("rumpe", 0);
		muscles.put("traps", 0);
		muscles.put("hofteUt", 0);
		muscles.put("bryst", 0);
		muscles.put("hamstring", 0);
		muscles.put("hofteIn", 0);
		
		// Denne skal oppdatere verdiene m/ data fra databasen
		updateBody(SQLConnector.getSessions(this.user.getId()));
	}
	
	public Map<String, Double> getMusclesWithPrecentages(){
		
		Map<String, Double> musclesWithPrecentage = new HashMap<>();
		
		for (String name : muscles.keySet()){
			double precentage = Double.valueOf(muscles.get(name))/Double.valueOf(totalValue);
			musclesWithPrecentage.put(name, precentage);
		}
		
		return musclesWithPrecentage;
	}
	
	// Her sender vi alle okter fra databasen
	public void updateBody(List<Session> sessions){
		for (Session session : sessions){
			List<String> musclesEachSession = SQLConnector.getMusclesTrained(session.getId());
			for(String muscle : musclesEachSession){
				totalValue += 1;
				updateMuscle(muscle, 1); 
			}
		}
	}
	

	public void updateMuscle(String name, int value){
		muscles.put(name, muscles.get(name) + value); 
	}
	
}




