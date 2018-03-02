package tdt4140.gr1837.app.core;

import java.util.ArrayList;
import java.util.List;

public class Trainer {
	private String name;
	private String adress;
	private String phoneNumber;
	private String mail;
	private int id;
	private List<User> clients = new ArrayList<>();
	
	public Trainer(String name, String adress, String phoneNumber, String mail, int id) {
		this.name = name;
		this.adress = adress;
		this.phoneNumber = phoneNumber;
		this.mail = mail;
		this.id = id;
	}
	
	public void addClient(int id) {
		User user = UserDatabase.getUserById(id);
		clients.add(user);
		user.setTrainer(this);
	}
	
	public String getName(){
		return name;
	}
	
	public String getAdress() {
		return adress;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getMail() {
		return mail;
	}

	public int getId() {
		return id;
	}
	
	public List<User> getClients() {
		return clients;
	}
	@Override
	public String toString() {
		return name;
	}
}
