package application;

import baseClasses.User;

public class userLogged{
	private static User utente;
	public userLogged(int id, String name, String surname, String email, int userType) {
		utente = new User(id, name, surname, email, userType);
		
	}
	
	public userLogged(User u) {
		utente = u;
	}
	
	public userLogged() {}
	
	public boolean isLogged() {
		if(utente != null)
			return true;
		return false;
	}
	
	public boolean isAdmin() {
		System.out.println("tipo utente: "+utente.getUserType());
		if(isLogged() && utente.getUserType() == 1)
			return true;
		return false;
	}
	
	public void logout() {
		utente = null;
	}
	
	public User getUtente() {
		return utente;
	}
	
	public String toString() {
		String s = utente.getName().toUpperCase() + " " + utente.getSurname().toUpperCase();
		return s;
	}
}
