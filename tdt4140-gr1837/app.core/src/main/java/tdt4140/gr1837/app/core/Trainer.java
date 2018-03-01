package tdt4140.gr1837.app.core;

import java.util.ArrayList;
import java.util.List;

public class Trainer {
	private String name;
	private String adress;
	private String phoneNumber;
	private String mail;
	private List<User> clients = new ArrayList<>();
	
	public Trainer(String name, String adress, String phoneNumber, String mail) {
		this.name=name;
		this.adress=adress;
		this.phoneNumber=phoneNumber;
		this.mail=mail;
	}
	
	public void addClient(int id) {
		User user=UserDatabase.getUserById(id);
		clients.add(user);
		user.setTrainer(this);
	}
	
	public String name(){
		return name;
	}
	
	public String adress() {
		return adress;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getMail() {
		return mail;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
