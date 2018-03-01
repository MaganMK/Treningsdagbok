package tdt4140.gr1837.app.core;

import java.util.ArrayList;
import java.util.List;

public class Trainer {
	private String name;
	private String adress;
	private String tlf;
	private String mail;
	private List<User> clients = new ArrayList<>();
	
	public Trainer(String name, String adress, String tlf, String mail) {
		this.name=name;
		this.adress=adress;
		this.tlf=tlf;
		this.mail=mail;
	}
	
	public void addClient(int id) {
		//UserDatabase
	}
	@Override
	public String toString() {
		return name;
	}
}
