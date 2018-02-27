package tdt4140.gr1837.app.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Klasse for aa haandtere "muskelmannen"
public class MuscleImage {
	
	public static List<String> names = new ArrayList<>();
	public static List<Integer> values = new ArrayList<>();

	/*
	 *  Ved aa bruke hashmap kan man refferere til riktig muskel ved navn.
	 *  Slik at det blir lettere aa oppdatere med navn fra databasen.
	 */
	private Map<String, Double> muscles = new HashMap<>();
	
	// Totalt antall verdier som er gitt, slik at vi kan regne prosent
	private int totalValue = 0;
	

	public MuscleImage(){
		muscles.put("underarmer", 0.0);
		muscles.put("abs", 0.0);
		muscles.put("triceps", 0.0);
		muscles.put("teres", 0.0);
		muscles.put("postDelts", 0.0);
		muscles.put("obliques", 0.0);
		muscles.put("lats", 0.0);
		muscles.put("frontDelts", 0.0);
		muscles.put("erector", 0.0);
		muscles.put("calves", 0.0);
		muscles.put("biceps", 0.0);
		muscles.put("quads", 0.0);
		muscles.put("rumpe", 0.0);
		muscles.put("traps", 0.0);
		muscles.put("hofteUt", 0.0);
		muscles.put("bryst", 0.0);
		muscles.put("hamstring", 0.0);
		muscles.put("hofteIn", 0.0);
		
		
		// Dummy data --- Trent jevnt bryst, abs og quads
		
		
		names.add("bryst");
		values.add(10);
		names.add("abs");
		values.add(10);
		names.add("quads");
		values.add(10);
		
		
		// Dummy data --- Trent masse bryst og biceps og litt quads
		/*
		names.add("bryst");
		values.add(50);
		names.add("biceps");
		values.add(50);
		names.add("quads");
		values.add(10);
		*/
		
		// Dummy data --- Trent masse triceps og ørlite traps (I forhold til hverandre)
		/*
		names.add("triceps");
		values.add(10);
		names.add("traps");
		values.add(2);
		*/
		
		// Denne skal oppdatere verdiene naar applikasjonen launcher m/ data fra databasen
		updateBody(names, values);
	}
	
	public Map<String, Double> getMuscles(){
		return muscles;
	}
	
	// Her maa vi sende inn verdier fra databasen 
	public void updateBody(List<String> names, List<Integer> values)
	{
		// Oppdaterer totalValue
		for (int value : values)
		{
			totalValue += value;
		}
		
		
		for (int i = 0; i<names.size(); i++)
		{
			updateMuscle(names.get(i), values.get(i));
		}
	}
	

	public void updateMuscle(String name, int value){
		double precent = Double.valueOf(value)/Double.valueOf(totalValue);
		
		//Legger til 0.1 fordi mange muskelgrupper = alt er trent jevnt blir relativt lav prosent pr muskel
		muscles.put(name, precent + 0.1); 
	}
	
}




