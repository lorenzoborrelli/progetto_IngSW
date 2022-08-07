package application;

public class userLogged{
	static User utente;
	public userLogged(int id, String name, String surname, String email, int userType) {
		utente = new User(id, name, surname, email, userType);
	}
	
	public userLogged(User u) {
		utente = u;
	}
	
	public boolean isLogged() {
		if(utente != null)
			return true;
		return false;
	}
}
