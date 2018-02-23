package tdt4140.gr1837.app.core;

// Midlertidig user-klasse til a fylle userdatabasen, testes derfor ikke
public class User {
	
	public String name;
	
	public User(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return getName();
	}

}
